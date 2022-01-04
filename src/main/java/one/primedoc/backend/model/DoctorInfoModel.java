package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.enums.InfoType;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorInfoModel {
    private String name;
    private String organizationName;
    private InfoType infoType;
    private Date start;
    private Date end;
}
