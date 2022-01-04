package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.DoctorInformationEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.repository.DoctorInformationRepository;
import one.primedoc.backend.service.DoctorInformationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DoctorInformationServiceImpl implements DoctorInformationService {
    private final DoctorInformationRepository doctorInfoRepository;

    public DoctorInformationServiceImpl(DoctorInformationRepository doctorInfoRepository) {
        this.doctorInfoRepository = doctorInfoRepository;
    }

    @Override
    public List<DoctorInformationEntity> getAll() {
        return doctorInfoRepository.findAll();
    }

    @Override
    public DoctorInformationEntity getById(Long id) {
        return doctorInfoRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(DoctorInformation) Record not found with id: " + id));
    }

    @Override
    public DoctorInformationEntity create(DoctorInformationEntity doctorInfo) {
        return doctorInfoRepository.save(doctorInfo);
    }

    @Override
    public DoctorInformationEntity updateById(Long id, DoctorInformationEntity doctorInfo) {
        return doctorInfoRepository.findById(id)
                .map(newDoctorInformation -> {
                    newDoctorInformation.setInfoType(doctorInfo.getInfoType());
                    newDoctorInformation.setName(doctorInfo.getName());
                    newDoctorInformation.setOrganizationName(doctorInfo.getOrganizationName());
                    newDoctorInformation.setStart(doctorInfo.getStart());
                    newDoctorInformation.setEnd(doctorInfo.getEnd());
                    return doctorInfoRepository.save(newDoctorInformation);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(DoctorInformation) Record not found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {
        doctorInfoRepository.deleteById(id);
        return "(DoctorInformation) Record with id: " + id + " has been deleted!";
    }

}
