package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "interval")
public class IntervalEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "interval_seq", sequenceName = "interval_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interval_seq")
    private Long id;

    @NotNull
    @Column(name = "start_time")
    private Time start;

    @NotNull
    @Column(name = "end_time")
    private Time end;

//    @NotNull
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private WeekEntity week;

//    @NotNull
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private WeekDayEntity weekDay;
}
