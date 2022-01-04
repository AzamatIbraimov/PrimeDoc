package one.primedoc.backend.entity;


import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payment")
public class PaymentEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    private Long id;

    @NotNull
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "logo")
    private String logo;

    @Column(name = "next_steps", length = 510)
    private String nextSteps;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="payment_id")
    @OrderBy("number")
    private List<PaymentStepEntity> paymentSteps = new ArrayList<>();
}
