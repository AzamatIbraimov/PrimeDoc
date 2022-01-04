package one.primedoc.backend.repository;

import one.primedoc.backend.entity.WeekDayEntity;
import one.primedoc.backend.entity.WeekEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface WeekDayRepository extends JpaRepository<WeekDayEntity, Long> {
    @Query(value = "SELECT * FROM week_day wd WHERE wd.week_id = ?1 ", nativeQuery = true)
    public List<WeekDayEntity> findByWeekId( Long weekId);
    @Query(value = "SELECT * FROM week_day wd WHERE wd.week_id = ?1 AND wd.week_day_name = ?2", nativeQuery = true)
    public WeekDayEntity findByWeekDayNameAndWeekId(Long weekId, String weekDayName);
}