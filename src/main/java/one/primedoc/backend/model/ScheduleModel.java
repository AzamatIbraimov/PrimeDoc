package one.primedoc.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.DoctorEntity;
import one.primedoc.backend.entity.WeekEntity;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleModel {
    @NotNull
    private Long doctorId;

    private List<WeekEntity> weeks = new ArrayList<>();

    private Integer weekDuration;

    private Integer currentWeek;

}
