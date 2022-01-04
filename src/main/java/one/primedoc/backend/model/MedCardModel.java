package one.primedoc.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedCardModel {
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthDate;
    private String medCardPhoneNumber;
}
