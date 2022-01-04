package one.primedoc.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "client")
public class ClientEntity extends BaseEntity {
    @Id
    @SequenceGenerator(name = "client_seq", sequenceName = "client_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    private Long id;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "image")
    private String image;

    @Column(name = "med_card_phone_number")
    private String medCardPhoneNumber;

}
