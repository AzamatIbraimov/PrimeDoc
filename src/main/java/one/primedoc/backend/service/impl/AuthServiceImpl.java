package one.primedoc.backend.service.impl;

import one.primedoc.backend.dao.JwtDao;
import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.enums.MessageType;
import one.primedoc.backend.enums.ResultCode;
import one.primedoc.backend.enums.Role;
import one.primedoc.backend.exception.InvalidValueException;
import one.primedoc.backend.model.RegistrationModel;
import one.primedoc.backend.model.ResponseMessage;
import one.primedoc.backend.model.jwt.JwtRefreshRequest;
import one.primedoc.backend.model.jwt.JwtRequest;
import one.primedoc.backend.model.jwt.JwtReset;
import one.primedoc.backend.model.jwt.JwtResponse;
import one.primedoc.backend.model.response.SmsMessageResponse;
import one.primedoc.backend.service.AuthService;
import one.primedoc.backend.service.ClientService;
import one.primedoc.backend.service.SmsService;
import one.primedoc.backend.service.UserService;
import one.primedoc.backend.service.jwt.JwtTokenUserDetailsService;
import one.primedoc.backend.service.jwt.JwtTokenUtil;
import one.primedoc.backend.utils.RandomGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtDao jwtDao;
    private final SmsService smsService;
    private final JwtTokenUserDetailsService jwtUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final Random random;
    private final ClientService clientService;

    public AuthServiceImpl(JwtTokenUtil jwtTokenUtil, SmsService smsService, JwtTokenUserDetailsService jwtUserDetailsService, UserService userService, ClientService clientService, JwtDao jwtDao) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.smsService = smsService;
        this.jwtDao = jwtDao;
        this.clientService = clientService;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.userService = userService;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        this.random = new Random();
    }

    @Override
    public ResponseEntity<?> login(JwtRequest request) {
        UserEntity user = jwtUserDetailsService.loadUserByUsername(request.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("(JwtAuth) User not found with username: " + request.getUsername());
        }
        JwtResponse jwtResponse = new JwtResponse();
        if (user.getUsername().equals(request.getUsername()) && bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println(user.getId());
            jwtResponse.setId(jwtDao.getIdByRole(user.getId(), user.getAuthorities().iterator().next()));
            jwtResponse.setUserId(user.getId());
            return getResponseEntity(user, jwtResponse);
//            return getResponseEntity(user.getUsername(), jwtResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<?> refresh(JwtRefreshRequest refresh) {
        UserEntity user = jwtUserDetailsService.loadUserByUsername(refresh.getUsername());
        if (user == null)
            throw new UsernameNotFoundException("(JwtAuth) User not found with username: " + jwtTokenUtil.getUsernameFromToken(refresh.getAccessToken()));
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setId(user.getId());
        if (jwtTokenUtil.validateRefreshToken(refresh.getRefreshToken(), refresh.getAccessToken())) {
            return getResponseEntity(user, jwtResponse);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> getResponseEntity(UserEntity user, JwtResponse jwtResponse) {
        String accessToken = jwtTokenUtil.generateToken(user.getUsername(), "access");
//        String accessToken = jwtTokenUtil.generateToken(username, "access");
        String refreshToken = jwtTokenUtil.generateToken(accessToken, "refresh");
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
        jwtResponse.setTokenExpirationTime(jwtTokenUtil.getExpirationDateFromToken(accessToken));
        jwtResponse.setRefreshExpirationTime(jwtTokenUtil.getExpirationDateFromToken(refreshToken));
        return ResponseEntity.accepted().body(jwtResponse);
    }

    @Override
    public ResponseEntity<?> register(RegistrationModel request) {
        System.out.println(!request.getAuthorities().equals(Collections.singleton(Role.ADMIN)));
        if (request.getAuthorities().equals(Collections.singleton(Role.USER))) {
            clientService.create(request);
        } else if (request.getAuthorities().equals(Collections.singleton(Role.CUSTOMER))){
            //TODO doctor self registration
            System.out.println("//TODO doctor self registration");
        }
        return ResponseEntity.accepted().build();
    }

    @Override
    public ResponseEntity<?> verify(String token) {
        System.out.println(userService.updateByVerification(token));
        return ResponseEntity.ok().build();
    }

    @Override
    public void reset(JwtReset reset) {
        if (bCryptPasswordEncoder.matches(reset.getConfirmPassword(), bCryptPasswordEncoder.encode(reset.getPassword()))){
            userService.updateByRecovery(reset.getToken(), reset.getPassword());
        } else {
            throw new InvalidValueException("Password mismatch!");
        }
    }

    @Override
    public SmsMessageResponse recovery(String phone) {
        return smsService.sendSMS(phone, userService.updateVerification(phone, RandomGenerator.code()).getVerification(), MessageType.RECOVERY);
    }
}
