package one.primedoc.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Time;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "reservation")
public class ReservationEntity extends BaseEntity {

    @Id
    @SequenceGenerator(name = "reservation_seq", sequenceName = "reservation_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_seq")
    private Long id;

    @Basic
    @NotNull
//    @Temporal(TemporalType.TIME)
    @Column(name = "start_time", nullable = false)
    private Time start;

    @Basic
    @NotNull
//    @Temporal(TemporalType.TIME)
    @Column(name = "end_time", nullable = false)
    private Time end;

    @Column(name = "phone_number", length = 17)
    private String phoneNumber;

    @Column(name = "comment")
    private String comment;

    @Column(name = "bill")
    private String bill;

    @NotNull
    @Column(name = "is_paid", columnDefinition = "bool default 'f'", nullable = false)
    private Boolean paid = false;

    @Column(name = "check_url")
    private String checkUrl;

    @NotNull
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

}
