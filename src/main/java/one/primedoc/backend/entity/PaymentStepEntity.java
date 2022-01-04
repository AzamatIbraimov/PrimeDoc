package one.primedoc.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment_step")
public class PaymentStepEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "payment_step_seq", sequenceName = "payment_step_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_step_seq")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Column(length = 510, name = "text", nullable = false)
    private String text;

//    @NotNull
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    private PaymentEntity payment;
}
