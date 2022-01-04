package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "schedule")
public class ScheduleEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "schedule_seq", sequenceName = "schedule_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "schedule_seq")
    private Long id;

    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private DoctorEntity doctor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id")
    private List<WeekEntity> weeks = new ArrayList<>();

    @NotNull
    @Column(name = "week_duration", nullable = false)
    private Integer weekDuration;

    @Column(name = "current_week")
    private Integer currentWeek;

    @Column(name = "is_generated", columnDefinition = "boolean default false", nullable = false)
    private Boolean isGenerated = false;

}
