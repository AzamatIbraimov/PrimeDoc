package one.primedoc.backend.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPersonalInfoModel {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private List<CategoryNameModel> categories;

}
