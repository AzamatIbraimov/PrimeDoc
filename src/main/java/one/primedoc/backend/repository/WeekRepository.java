package one.primedoc.backend.repository;

import one.primedoc.backend.entity.WeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;

public interface WeekRepository extends JpaRepository<WeekEntity, Long> {
    @Query(value = "SELECT * FROM week w WHERE w.schedule_id = ?1 and w.week_order = ?2", nativeQuery = true)
    public WeekEntity findByWeekOrderAndScheduleId(Long scheduleId, Integer weekOrder);
}
