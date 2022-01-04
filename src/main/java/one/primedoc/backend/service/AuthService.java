package one.primedoc.backend.service;

import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.model.RegistrationModel;
import one.primedoc.backend.model.jwt.JwtRefreshRequest;
import one.primedoc.backend.model.jwt.JwtRequest;
import one.primedoc.backend.model.jwt.JwtReset;
import one.primedoc.backend.model.response.SmsMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    public ResponseEntity<?> login(JwtRequest user);
    public ResponseEntity<?> refresh(JwtRefreshRequest refresh);
    public ResponseEntity<?> register(RegistrationModel user);
    public ResponseEntity<?> verify(String token);
    public SmsMessageResponse recovery(String token);
    public void reset(JwtReset reset);
}
