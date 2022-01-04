package one.primedoc.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationModel {
    @NotNull
    private String username;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
//    private String firstName;
//    private String lastName;
//    private String patronymic;
//    private Date birthDate;
    private Set<Role> authorities;
}
