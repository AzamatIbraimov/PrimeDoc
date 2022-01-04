package one.primedoc.backend.service.impl;


import one.primedoc.backend.dao.DoctorDao;
import one.primedoc.backend.entity.*;
import one.primedoc.backend.enums.Role;
import one.primedoc.backend.exception.DuplicateException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.StorageException;
import one.primedoc.backend.model.*;
import one.primedoc.backend.repository.CategoryRepository;
import one.primedoc.backend.repository.DoctorRepository;
import one.primedoc.backend.repository.ScheduleRepository;
import one.primedoc.backend.repository.WorkTimeRepository;
import one.primedoc.backend.service.DoctorService;
import one.primedoc.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorDao doctorDao;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ScheduleRepository scheduleRepository;
    private final WorkTimeRepository workTimeRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(DoctorServiceImpl.class);


    public DoctorServiceImpl(DoctorRepository doctorRepository, CategoryRepository categoryRepository, UserService userService, DoctorDao doctorDao, ScheduleRepository scheduleRepository, WorkTimeRepository workTimeRepository) {
        this.doctorRepository = doctorRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.doctorDao = doctorDao;
        this.scheduleRepository = scheduleRepository;
        this.workTimeRepository = workTimeRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Value("${doctor.image.upload.path}")
    private String path;

    @Value("${image.url}")
    private String imageUrl;

    @Override
    public List<DoctorEntity> getAll() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorEntity getById(Long id) {
        return doctorRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Doctor) Record not found with id: " + id));
    }

    @Override
    public Long getDoctorIdByReservationId(Long id) {
        return doctorDao.getIdByReservationId(id);
    }

    @Override
    public DoctorCreatedModel create(DoctorModel doctor, MultipartFile imageFile) {
        if (userService.existsByUsername(doctor.getUsername())) {
            throw new DuplicateException("(User) Record already exists with username: " + doctor.getUsername());
        }
        DoctorEntity doctorEntity = doctorRepository.save(DoctorEntity.builder()
                .bio(doctor.getBio())
                .position(doctor.getPosition())
                .image(createImage(imageFile, doctor))
                .information(doctor.getInformation().stream().map(doctorInfoModel ->
                        DoctorInformationEntity.builder()
                                .start(doctorInfoModel.getStart())
                                .end(doctorInfoModel.getEnd())
                                .infoType(doctorInfoModel.getInfoType())
                                .name(doctorInfoModel.getName())
                                .organizationName(doctorInfoModel.getOrganizationName())
                                .build()).collect(Collectors.toList()))
                .user(UserEntity.builder()
                        .username(doctor.getUsername())
                        .password(bCryptPasswordEncoder.encode(doctor.getPassword()))
                        .firstName(doctor.getFirstName())
                        .lastName(doctor.getLastName())
                        .patronymic(doctor.getPatronymic())
                        .birthDate(doctor.getBirthDate())
                        .authorities(Collections.singleton(Role.CUSTOMER))
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .enabled(true)
                        .build()).build());
        if (doctor.getCategories() != null && !doctor.getCategories().isEmpty()) {
            doctorDao.attachByDoctorId(doctorEntity.getId(), doctor.getCategories());
        }
        DoctorCreatedModel doctorCreatedModel = DoctorCreatedModel.builder()
                .id(doctorEntity.getId())
                .doctor_id(doctorEntity.getUser().getId())
                .username(doctorEntity.getUser().getUsername())
                .firstName(doctorEntity.getUser().getFirstName())
                .lastName(doctorEntity.getUser().getLastName())
                .patronymic(doctorEntity.getUser().getPatronymic())
                .birthDate(doctorEntity.getUser().getBirthDate())
                .position(doctorEntity.getPosition())
                .image(doctorEntity.getImage())
                .bio(doctorEntity.getBio())
                .information(doctorEntity.getInformation().stream().map(doctorInfoModel ->
                        DoctorInfoModel.builder()
                                .start(doctorInfoModel.getStart())
                                .end(doctorInfoModel.getEnd())
                                .infoType(doctorInfoModel.getInfoType())
                                .name(doctorInfoModel.getName())
                                .organizationName(doctorInfoModel.getOrganizationName())
                                .build()).collect(Collectors.toList())
                )
                .categories(doctorDao.getCategoryIdByDoctorId(doctorEntity.getId()).stream().map(categoryId ->
                        CategoryNameModel.builder()
                                .id(categoryId)
                                .name(categoryRepository.getOne(categoryId).getName())
                                .build()).collect(Collectors.toList()))
                .build();
        return doctorCreatedModel;
    }

    @Override
    public DoctorEntity updateById(Long id, DoctorDataChangeModel doctor) {
        return doctorRepository.save(doctorRepository.findById(id).map(doctorEntity -> {
            doctorEntity.getUser().setFirstName(doctor.getFirstName());
            doctorEntity.getUser().setLastName(doctor.getLastName());
            doctorEntity.getUser().setPatronymic(doctor.getPatronymic());
            doctorEntity.getUser().setUsername(doctor.getUsername());
            if (doctor.getCategories() != null && !doctor.getCategories().isEmpty()) {
                doctorDao.detachByDoctorId(id);
                doctorDao.attachByDoctorId(id, doctor.getCategories());
            }
            return doctorEntity;
        }).orElseThrow(() -> new RecordNotFoundException("(Doctor) Record not found with id: " + id)));
    }

    @Override
    public String deactivateById(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Doctor) Record not found with id: " + id));
        if (doctor.getDeleted() == null) {
            doctorDao.detachByDoctorId(id);
            doctor.setDeleted(new Date());
            List<ScheduleEntity> scheduleEntityList = scheduleRepository.findAllByDoctor_Id(id);
            // если у него уже есть расписание
            for (ScheduleEntity scheduleEntity : scheduleEntityList) scheduleRepository.delete(scheduleEntity);
            doctorRepository.save(doctor);
        } else {
            return "(Doctor) Record with id: " + id + " already deactivated!";
        }
        return "(Doctor) Record with id: " + id + " has been deactivated!";
    }

    @Override
    public String deleteById(Long id) {
        DoctorEntity doctor = doctorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Doctor) Record not found with id: " + id));
        if (doctor.getDeleted() != null) {
            List<WorkTimeEntity> workTimes = workTimeRepository.findAllByDoctor_Id(id);
            for (WorkTimeEntity workTime : workTimes) workTime.setDoctor(null);
            doctorRepository.delete(doctor);
            return "(Doctor) Record with id: " + id + " has been deleted!";
        } else {
            return "(Doctor) Record with id: " + id + " not deactivated!";
        }
    }

    //TODO Pagination and Optimized query
    @Override
    public List<DoctorPersonalInfoModel> getAllDoctorsData() {
        return doctorDao.getAllDoctorsData();
    }

    @Override
    public DoctorDetailsModel getDoctorDetails(Long id) {
        return doctorDao.getDoctorDetails(id);
    }

    @Override
    public DoctorFullInfoModel getDoctorFullInfoById(Long id) {
        return doctorDao.getDoctorFullInfoById(id);
    }

    @Override
    public String createImage(MultipartFile imageFile, DoctorModel doctor) {
        if (imageFile != null) {
            try {
                String code = UUID.randomUUID().toString();
                String fileName = imageFile.getOriginalFilename();
                InputStream is = imageFile.getInputStream();
                assert fileName != null;
                Files.copy(is, Paths.get(path + doctor.getUsername()
                                + code + fileName.substring(fileName.lastIndexOf("."))),
                        StandardCopyOption.REPLACE_EXISTING);
                return (imageUrl + doctor.getUsername()
                        + code + fileName.substring(fileName.lastIndexOf(".")));
            } catch (IOException e) {
                String msg = "Failed to store this file";
                throw new StorageException(msg, e);
            }
        } else {
            return null;
        }
    }

    @Override
    public DoctorEntity updateImageById(Long id, MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        DoctorEntity doctor = doctorRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Doctor) Record not found with id: " + id));
        try {
            String code = UUID.randomUUID().toString();
            String fileName = imageFile.getOriginalFilename();
            InputStream is = imageFile.getInputStream();
            assert fileName != null;
            Files.copy(is, Paths.get(path + doctor.getUser().getUsername()
                            + code + fileName.substring(fileName.lastIndexOf("."))),
                    StandardCopyOption.REPLACE_EXISTING);
            doctor.setImage(imageUrl + doctor.getUser().getUsername()
                    + code + fileName.substring(fileName.lastIndexOf(".")));
            return doctorRepository.save(doctor);
        } catch (IOException e) {
            String msg = "Failed to store this file";
            throw new StorageException(msg, e);
        }
    }

    @Override
    public DoctorReservationModel getDoctorDataById(Long id) {
        return doctorDao.getDoctorDataById(id);
    }


}
