package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHistoryModel {
    private Long id;
    private String phoneNumber;
    @NotNull
    private String start;
    @NotNull
    private Date end;
    @NotNull
    private String checkUrl;
    private DoctorReservationModel doctor;
}
