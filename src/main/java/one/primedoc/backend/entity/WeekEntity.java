package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "week")
public class WeekEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "week_seq", sequenceName = "week_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_seq")
    private Long id;

    @NotNull
    @Column(name = "week_order", nullable = false)
    private Integer weekOrder = 1;
//
//    @NotNull
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private ScheduleEntity schedule;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="week_id")
    private List<WeekDayEntity> weekDays = new ArrayList<>();
}
