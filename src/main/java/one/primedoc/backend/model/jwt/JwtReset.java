package one.primedoc.backend.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtReset {
    private String token;
    private String password;
    private String confirmPassword;
}
