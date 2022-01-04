package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.PaymentEntity;
import one.primedoc.backend.entity.PaymentStepEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.StorageException;
import one.primedoc.backend.repository.PaymentRepository;
import one.primedoc.backend.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Value("${payment.image.upload.path}")
    private String path;

    @Value("${image.url}")
    private String imageUrl;

    @Override
    public List<PaymentEntity> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public PaymentEntity getById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Payment) Record not found with id: " + id));
    }

    @Override
    public PaymentEntity create(PaymentEntity payment, MultipartFile imageFile) {
        if (imageFile != null) {
            if (imageFile.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            try {
                String fileName = imageFile.getOriginalFilename();
                InputStream is = imageFile.getInputStream();
                assert fileName != null;
                Files.copy(is, Paths.get(path + payment.getName()
                                + "_" + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                payment.setLogo(imageUrl + payment.getName()
                        + "_" + fileName);
                return paymentRepository.save(payment);
            } catch (IOException e) {
                String msg = "Failed to store this file";
                throw new StorageException(msg, e);
            }
        } else {
            return paymentRepository.save(payment);
        }
    }

    @Override
    public PaymentEntity updateById(Long id, PaymentEntity payment) {
        return paymentRepository.findById(id)
                .map(newPayment -> {
                    newPayment.setName(payment.getName());
                    newPayment.setNextSteps(payment.getNextSteps());
                    newPayment.getPaymentSteps().clear();
                    newPayment.getPaymentSteps().addAll(payment.getPaymentSteps());
                    return paymentRepository.save(newPayment);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Payment) Record not found with id: " + id));
    }

    @Override
    public PaymentEntity updateLogoById(Long id, MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        try {
            PaymentEntity payment = paymentRepository.getOne(id);
            try {
                String fileName = imageFile.getOriginalFilename();
                InputStream is = imageFile.getInputStream();
                assert fileName != null;
                Files.copy(is, Paths.get(path + payment.getName()
                                + fileName.substring(fileName.lastIndexOf("."))),
                        StandardCopyOption.REPLACE_EXISTING);
                payment.setLogo(imageUrl + payment.getName()
                        + fileName.substring(fileName.lastIndexOf(".")));
                return paymentRepository.save(payment);
            } catch (IOException e) {

                String msg = "Failed to store this file";

                throw new StorageException(msg, e);
            }
        } catch (RecordNotFoundException rc) {
            throw new RecordNotFoundException("(Payment) Record not found with id: " + id);
        }

    }

    @Override
    public String deleteById(Long id) {
        String imageFileURL = paymentRepository.getOne(id).getLogo();
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
        paymentRepository.deleteById(id);
        return "(Payment) Record with id: " + id + " has been deleted!";
    }
}