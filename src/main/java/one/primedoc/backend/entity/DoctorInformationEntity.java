package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;
import one.primedoc.backend.enums.InfoType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "doctor_info")
public class DoctorInformationEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "doctor_info_seq", sequenceName = "doctor_info_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_info_seq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "organization_name")
    private String organizationName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "info_type")
    private InfoType infoType;

    @Basic
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date start;

    @Basic
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date end;
}
