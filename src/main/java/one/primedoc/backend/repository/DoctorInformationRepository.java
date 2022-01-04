package one.primedoc.backend.repository;

import one.primedoc.backend.entity.DoctorEntity;
import one.primedoc.backend.entity.DoctorInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorInformationRepository extends JpaRepository<DoctorInformationEntity, Long> {
}
