package one.primedoc.backend.firebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.enums.Role;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FbModel {
    private String username;
    private String code;
}
