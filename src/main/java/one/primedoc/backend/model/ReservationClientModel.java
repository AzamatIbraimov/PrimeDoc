package one.primedoc.backend.model;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationClientModel {
    private Long id;
    private String phoneNumber;
    private String comment;
    @NotNull
    private Time start;
    @NotNull
    private Time end;
    @NotNull
    private ClientShortModel client;

}
