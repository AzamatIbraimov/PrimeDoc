package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorShortModel {
    private Long id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String image;
    private String position;

}
