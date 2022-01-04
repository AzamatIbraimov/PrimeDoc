package one.primedoc.backend.repository;

import one.primedoc.backend.entity.ReservationEntity;
import one.primedoc.backend.entity.WorkTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface WorkTimeRepository extends JpaRepository<WorkTimeEntity, Long> {
    public List<WorkTimeEntity> findAllByDoctor_Id(Long id);
    @Query("SELECT wt FROM work_time wt WHERE wt.doctor.id = :id AND wt.start > CURRENT_DATE")
    public List<WorkTimeEntity> findAllByDoctor_IdAndStartLessThan(Long id);
    public WorkTimeEntity findByReservation(ReservationEntity reservation);
}
