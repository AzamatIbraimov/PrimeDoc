package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.entity.ClientEntity;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInfoModel {
    private Long id;
    private Long clientId;
    private Long doctorId;
    private Long userClientId;
    private Long userDoctorId;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String phone;
    private Date date;
    private Date start;
    private Date end;
    private Boolean isPaid;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorPatronymic;
}
