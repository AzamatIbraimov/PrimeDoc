package one.primedoc.backend.repository;

import one.primedoc.backend.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    public Optional<ScheduleEntity> findByDoctorId(Long id);
    public List<ScheduleEntity> findAllByDoctor_Id(Long id);
}
