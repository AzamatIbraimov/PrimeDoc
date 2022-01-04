package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillModel {
    private String oid;
    private String sum;
    private String firstname;
    private String lastname;
    private String totalSum;
    private String last;
    private String authCode;
    private String type;
}
