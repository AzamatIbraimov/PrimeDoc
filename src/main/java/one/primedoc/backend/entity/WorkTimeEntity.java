package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "work_time")
public class WorkTimeEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "work_time_seq", sequenceName = "work_time_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_time_seq")
    private Long id;

    @Basic
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    private Date start;

    @Basic
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time", nullable = false)
    private Date end;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private DoctorEntity doctor;

//    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="work_time_id")
    private List<ReservationEntity> reservation;
}
