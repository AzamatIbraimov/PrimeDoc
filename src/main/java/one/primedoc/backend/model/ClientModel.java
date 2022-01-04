package one.primedoc.backend.model;

import lombok.*;
import one.primedoc.backend.entity.UserEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ClientModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String image;
}
