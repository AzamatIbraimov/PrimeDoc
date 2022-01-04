package one.primedoc.backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientShortModel {
    private Long id;
    private String firstname;
    private String lastname;
    private String patronymic;
}
