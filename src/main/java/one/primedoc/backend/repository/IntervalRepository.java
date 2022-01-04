package one.primedoc.backend.repository;

import one.primedoc.backend.entity.IntervalEntity;
import one.primedoc.backend.entity.WeekDayEntity;
import one.primedoc.backend.entity.WeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IntervalRepository extends JpaRepository<IntervalEntity, Long> {
    @Query(value = "SELECT * FROM interval i WHERE i.week_day_id = ?1", nativeQuery = true)
    public List<IntervalEntity> findAllByWeekDayId(Long weekDayId);
}
