package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "doctor")
public class DoctorEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "doctor_seq", sequenceName = "doctor_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_seq")
    private Long id;

    @NotNull
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "image")
    private String image;

    @Column(name = "position")
    private String position;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "deleted")
    private Date deleted;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleEntity> schedules = new ArrayList<>();

    @OneToMany(targetEntity = DoctorInformationEntity.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private List<DoctorInformationEntity> information = new ArrayList<>();


//
//    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WorkTimeEntity> intervals = new ArrayList<>();
}
