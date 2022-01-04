package one.primedoc.backend.service;

import one.primedoc.backend.entity.DoctorInformationEntity;

import java.util.List;

public interface DoctorInformationService {
    public List<DoctorInformationEntity> getAll();
    public DoctorInformationEntity getById(Long id);
    public DoctorInformationEntity create(DoctorInformationEntity doctorInfo);
    public DoctorInformationEntity updateById(Long id, DoctorInformationEntity doctorInfo);
    public String deleteById(Long id);
}
