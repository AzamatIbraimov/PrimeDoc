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
public class DoctorDataModel {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String position;
    private String image;
    private List<CategoryReservationModel> categories;
}
