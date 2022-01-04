package one.primedoc.backend.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private Long id;
    private Long userId;
    private String accessToken;
    private Date tokenExpirationTime;
    private String refreshToken;
    private Date refreshExpirationTime;

}
