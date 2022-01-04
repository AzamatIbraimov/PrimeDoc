package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorCreatedModel {
    private Long id;
    private Long doctor_id;
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String bio;
    private String position;
    private String image;
    private Date birthDate;
    private List<DoctorInfoModel> information;
    private List<CategoryNameModel> categories;
}
