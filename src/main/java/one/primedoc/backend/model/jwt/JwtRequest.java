package one.primedoc.backend.model.jwt;

import lombok.*;

import java.io.Serializable;
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
}