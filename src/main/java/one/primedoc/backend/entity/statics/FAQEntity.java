package one.primedoc.backend.entity.statics;


import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "faq")
public class FAQEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "faq_seq", sequenceName = "faq_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "faq_seq")
    private Long id;

    @NotNull
    @Column(columnDefinition="TEXT", name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(columnDefinition="TEXT", name = "answer", nullable = false)
    private String answer;

    @NotNull
    @Column(name = "`order`", nullable = false)
    private Integer order;

}
