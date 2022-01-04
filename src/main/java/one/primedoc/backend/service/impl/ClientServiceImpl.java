package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.enums.MessageType;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.StorageException;
import one.primedoc.backend.exception.UsernameInUseException;
import one.primedoc.backend.model.*;
import one.primedoc.backend.repository.ClientRepository;
import one.primedoc.backend.service.ClientService;
import one.primedoc.backend.service.SmsService;
import one.primedoc.backend.service.UserService;
import one.primedoc.backend.utils.RandomGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserService userService;
    private final SmsService smsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Logger LOGGER = LoggerFactory.getLogger(ClientServiceImpl.class);


    public ClientServiceImpl(ClientRepository clientRepository, SmsService smsService, UserService userService) {
        this.clientRepository = clientRepository;
        this.smsService = smsService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.userService = userService;
    }

    @Value("${client.image.upload.path}")
    private String path;

    @Value("${image.url}")
    private String imageUrl;


    @Override
    public List<ClientEntity> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public ClientEntity getById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Client) Record not found with id: " + id));
    }

    @Override
    public ClientEntity create(RegistrationModel client) {
        if (userService.isUsernameReserved(client.getUsername())) {
            throw new UsernameInUseException("There already is a user with this phone number: " + client.getUsername());
        }
        String code = RandomGenerator.code();
        ClientEntity clientEntity = clientRepository.save(
                ClientEntity.builder()
                        .user(UserEntity.builder()
                                .username(client.getUsername())
                                .password(bCryptPasswordEncoder.encode(client.getPassword()))
                                .authorities(client.getAuthorities())
                                .verification(code)
                                .enabled(false)
                                .accountNonLocked(false)
                                .accountNonExpired(false)
                                .credentialsNonExpired(false)
                                .build())
                        .build());
        smsService.sendSMS(client.getUsername(), code, MessageType.VERIFICATION);
        return clientEntity;
    }

    public void compress(String fullPath) throws IOException {
        File input = new File(fullPath);
        String format = fullPath.substring(fullPath.lastIndexOf(".") + 1);
        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File(fullPath);
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.3f);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }


    @Override
    public ClientEntity updateById(Long id, MultipartFile imageFile) {

        if (imageFile.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        try {
            ClientEntity client = clientRepository.findById(id).orElseThrow(() ->
                    new RecordNotFoundException("(Client) Record not found with id: " + id));
            try {
                String fileName = imageFile.getOriginalFilename();
                InputStream is = imageFile.getInputStream();
                assert fileName != null;
                String fullPath = path + client.getUser().getUsername() + fileName.substring(fileName.lastIndexOf("."));
                Files.copy(is, Paths.get(path + client.getUser().getUsername()
                                + fileName.substring(fileName.lastIndexOf("."))),
                        StandardCopyOption.REPLACE_EXISTING);
                compress(fullPath);
                client.setImage(imageUrl + client.getUser().getUsername()
                        + fileName.substring(fileName.lastIndexOf(".")));
                return clientRepository.save(client);
            } catch (IOException e) {
                String msg = "Failed to store this file";
                throw new StorageException(msg, e);
            }
        } catch (RecordNotFoundException rc) {
            throw new RecordNotFoundException("(Client) Record not found with id: " + id);
        }
    }

    @Override
    public MedCardModel updateMedCardById(Long id, MedCardModel med) {
        ClientEntity client = clientRepository.findById(id)
                .map(newClient -> {
                    newClient.getUser().setFirstName(med.getFirstName());
                    newClient.getUser().setLastName(med.getLastName());
                    newClient.getUser().setPatronymic(med.getPatronymic());
                    newClient.getUser().setBirthDate(med.getBirthDate());
                    newClient.setMedCardPhoneNumber(med.getMedCardPhoneNumber());
                    return clientRepository.save(newClient);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Client) Record not found with id: " + id));
        return getMedCardById(id);
    }

    @Override
    public MedCardModel getMedCardById(Long id) {
        return clientRepository.findById(id).map(medCard -> {
            return MedCardModel.builder()
                    .firstName(medCard.getUser().getFirstName())
                    .lastName(medCard.getUser().getLastName())
                    .patronymic(medCard.getUser().getPatronymic())
                    .birthDate(medCard.getUser().getBirthDate())
                    .medCardPhoneNumber(medCard.getMedCardPhoneNumber()).build();
        }).orElseThrow(() ->
                new RecordNotFoundException("(Client) Record not found with id: " + id));

    }

    @Override
    public Page<List<ClientModel>> getAllMedCards(Pageable pageable) {
        return clientRepository.getAllMedCards(pageable);
    }

    @Override
    public String deleteById(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Client) Record not found with id: " + id));
        String imageFileURL = client.getImage();
        if (imageFileURL != null) {
            String imageFileName = imageFileURL.substring(imageFileURL.lastIndexOf("/") + 1);
            Path filetoDeletePath = Paths.get(path + imageFileName);
            try {
                Files.delete(filetoDeletePath);
            } catch (IOException e) {
                String msg = "Failed to delete this file";

                throw new StorageException(msg, e);
            }
        }
        clientRepository.deleteById(id);
        return "(Client) Record with id: " + id + " has been deleted!";
    }

}
