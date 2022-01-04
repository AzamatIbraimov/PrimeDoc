package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "week_day")
public class WeekDayEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "week_day_seq", sequenceName = "week_day_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "week_day_seq")
    private Long id;

    @NotNull
    @Column(name = "week_day_name", nullable = false)
    private String weekDayName;

//    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
//    @JoinTable(name = "week_day_week", joinColumns = @JoinColumn(name = "week_day_id"), inverseJoinColumns = @JoinColumn(name = "week_id"))
//    @JsonIgnore
//    @ToString.Exclude
//    private List<WeekEntity> weeks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="week_day_id")
    private List<IntervalEntity> intervals = new ArrayList<>();
}
