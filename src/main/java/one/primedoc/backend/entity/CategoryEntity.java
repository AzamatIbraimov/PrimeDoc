package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "category")
public class CategoryEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    private Long id;
    @NotNull
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(columnDefinition="TEXT", name = "description", nullable = false)
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "illness", length = 512)
    private String illness;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "doctor_category",
            joinColumns = {@JoinColumn(name = "category_id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id")})
    private Set<DoctorEntity> doctors = new HashSet<>();
}
