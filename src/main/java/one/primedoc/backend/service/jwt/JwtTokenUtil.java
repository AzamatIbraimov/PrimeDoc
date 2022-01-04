package one.primedoc.backend.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import one.primedoc.backend.exception.InvalidValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    public static final long REFRESH_JWT_TOKEN_VALIDITY = 10 * 60 * 60 * 1000;

    @Value("${jwt.security.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String  generateToken(String value, String type) {
        Map<String, Object> claims = new HashMap<>();
        switch (type){
            case "access":
                return doGenerateToken(claims, value, JWT_TOKEN_VALIDITY);
            case "refresh":
                return doGenerateToken(claims, value, REFRESH_JWT_TOKEN_VALIDITY);
            default:
                throw new InvalidValueException("(JwtTokenUtil) Invalid value of token generation type. Must be 'access' or 'refresh' ");
        }
    }

    private String  doGenerateToken(Map<String, Object> claims, String subject, Long validity) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public Boolean validateRefreshToken(String refreshToken, String accessToken) {
        return (accessToken.equals(getUsernameFromToken(refreshToken)) && !isTokenExpired(refreshToken));
    }
}
