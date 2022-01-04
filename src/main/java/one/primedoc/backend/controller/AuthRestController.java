package one.primedoc.backend.controller;

import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.enums.ResultCode;
import one.primedoc.backend.exception.InvalidValueException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.RegistrationModel;
import one.primedoc.backend.model.ResponseMessage;
import one.primedoc.backend.model.jwt.JwtRefreshRequest;
import one.primedoc.backend.model.jwt.JwtRequest;
import one.primedoc.backend.model.jwt.JwtReset;
import one.primedoc.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/auth")
    public ResponseEntity<?> authentication(@RequestBody JwtRequest authenticationRequest) {
        return authService.login(authenticationRequest);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@RequestBody JwtRefreshRequest refreshRequest) {
        return authService.refresh(refreshRequest);
    }
    @PostMapping(value = "/register")
    public ResponseEntity<?> registration(@RequestBody RegistrationModel userEntity) {
        return authService.register(userEntity);
    }

    @PostMapping(value = "/verify/{token}")
    public ResponseEntity<?> verification(@PathVariable("token") String token) {
        return authService.verify(token);
    }

    @PostMapping(value = "/reset")
    public ResponseMessage reset(@RequestBody JwtReset jwtReset) {
        try {
            authService.reset(jwtReset);
            return ResponseMessage.builder().result(null).resultCode(ResultCode.SUCCESS).details("Password changed successfully").build();
        } catch (InvalidValueException exception) {
            return ResponseMessage.builder().result(null).resultCode(ResultCode.BAD_REQUEST).details("Password mismatch").build();
        } catch (RecordNotFoundException exception){
            return ResponseMessage.builder().result(null).resultCode(ResultCode.NOT_FOUND).details(exception.getMessage()).build();
        }
    }

    @PostMapping(value = "/recovery/{phone}")
    public ResponseMessage recovery(@PathVariable("phone") String phone) {
        try {
            authService.recovery(phone);
            return ResponseMessage.builder().result(null).resultCode(ResultCode.SUCCESS).details("Recovery code message has been sent").build();
        } catch (RecordNotFoundException exception) {
            return ResponseMessage.builder().result(null).resultCode(ResultCode.NOT_FOUND).details(exception.getMessage()).build();
        }
    }
}
