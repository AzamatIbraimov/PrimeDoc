package one.primedoc.backend.service;


import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.entity.DoctorEntity;
import one.primedoc.backend.model.*;
import one.primedoc.backend.service.impl.DoctorServiceImpl;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DoctorService {
    public List<DoctorEntity> getAll();
    public DoctorEntity getById(Long id);
    public Long getDoctorIdByReservationId(Long id);
    public DoctorCreatedModel create(DoctorModel doctor, MultipartFile imageFile);
    public DoctorEntity updateById(Long id, DoctorDataChangeModel doctor);
    public String deactivateById(Long id);
    public String deleteById(Long id);
    public List<DoctorPersonalInfoModel> getAllDoctorsData();
    public DoctorDetailsModel getDoctorDetails(Long id);
    public DoctorFullInfoModel getDoctorFullInfoById(Long id);
    public String createImage(MultipartFile imageFile, DoctorModel doctor);
    public DoctorEntity updateImageById(Long id, MultipartFile imageFile);
    public DoctorReservationModel getDoctorDataById(Long id);
}