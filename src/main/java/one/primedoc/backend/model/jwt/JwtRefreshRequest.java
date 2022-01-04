package one.primedoc.backend.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtRefreshRequest implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String username;
    private String accessToken;
    private String refreshToken;

}
