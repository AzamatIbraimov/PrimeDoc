package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorFullInfoModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String image;
    private String bio;
    private List<DoctorInfoModel> information;
    private List<CategoryInfoModel> categories;
}
