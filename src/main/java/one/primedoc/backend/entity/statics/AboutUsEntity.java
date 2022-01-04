package one.primedoc.backend.entity.statics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "about_us")
public class AboutUsEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "about_us_seq", sequenceName = "about_us_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "about_us_seq")
    private Long id;

    @NotNull
    @Column(name = "header", length = 255, nullable = false)
    private String header;

    @NotNull
    @Column(columnDefinition="TEXT", name = "paragraph", nullable = false)
    private String paragraph;
//
//    @Column(columnDefinition="TEXT", name = "image")
//    private String image;

    @NotNull
    @Column(name = "`order`", nullable = false)
    private Integer order;

}
