package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Time;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationModel {
    private String phoneNumber;
    private String comment;
    @NotNull
    private Time start;
    @NotNull
    private Time end;
    @NotNull
    private Long clientId;
    private Long worktimeId;
}
