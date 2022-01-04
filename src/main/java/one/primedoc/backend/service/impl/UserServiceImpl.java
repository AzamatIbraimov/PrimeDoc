package one.primedoc.backend.service.impl;

import one.primedoc.backend.dao.UserDao;
import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.enums.Role;
import one.primedoc.backend.exception.PasswordDoesNotMatch;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.UsernameInUseException;
import one.primedoc.backend.firebase.FbModel;
import one.primedoc.backend.model.request.ChangePasswordRequest;
import one.primedoc.backend.repository.UserRepository;
import one.primedoc.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Date now;
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository, UserDao userDao) {
        this.userRepository = userRepository;
        this.userDao = userDao;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
        now = new Date();
    }

    @Override
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(User) Record not found with id: " + id));
    }

    @Override
    public UserEntity getByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("(JwtAuth) User not found with username: " + username));
    }

    @Override
    public Boolean isUsernameReserved(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserEntity updateByFirebaseCode(FbModel code) {
        return userRepository.findByUsername(code.getUsername()).map(newUser -> {
            newUser.setFirebaseToken(code.getCode());
            return userRepository.save(newUser);
        }).orElseThrow(() -> new UsernameNotFoundException("(JwtAuth) User not found with username: " + code.getUsername()));
    }

    @Override
    public ResponseEntity<?> create(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameInUseException("There already is a user with this phone number: " + user.getUsername());
        }
        userRepository.save(user);
        return ResponseEntity.accepted().build();
    }

    @Override
    public UserEntity updateById(Long id, UserEntity user) {
        return userRepository.findById(id)
                .map(newUser -> {
                    newUser.setFirstName(user.getFirstName());
                    newUser.setLastName(user.getLastName());
                    newUser.setPatronymic(user.getPatronymic());
                    newUser.setBirthDate(user.getBirthDate());
                    return userRepository.save(newUser);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(User) Record not found with id: " + id));
    }

    @Override
    public UserEntity updateVerification(String phone, String code) {
        return userRepository.findByUsername(phone) //.filter(userEntity -> now.getTime() - userEntity.getUpdatedDate().getTime() >= 60*1000)
                .map(newUser -> {
                    newUser.setVerification(code);
                    return userRepository.save(newUser);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(User) Record not found with username: " + phone));
    }

    @Override
    public UserEntity updateByVerification(String token) {
        UserEntity user = userRepository.findByVerification(token)
                .map(newUser -> {
                    newUser.setVerification("");
                    newUser.setEnabled(true);
                    newUser.setAccountNonLocked(true);
                    newUser.setAccountNonExpired(true);
                    newUser.setCredentialsNonExpired(true);
                    return userRepository.save(newUser);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(User) Record not found with verification token: " + token));
        return user;
    }


    @Override
    public UserEntity updateByRecovery(String token, String password) {
        return userRepository.findByVerification(token)
                .map(newUser -> {
                    newUser.setVerification("");
                    newUser.setPassword(bCryptPasswordEncoder.encode(password));
                    newUser.setAccountNonLocked(false);
                    return userRepository.save(newUser);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(User) Record not found with verification token: " + token));
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserEntity updateUsersPassword(ChangePasswordRequest changePasswordRequest) {
        String hash = bCryptPasswordEncoder.encode(changePasswordRequest.getNewPassword());
        if (!bCryptPasswordEncoder.matches(changePasswordRequest.getConfirmPassword(), hash)) {
            throw new PasswordDoesNotMatch("(User) Password and confirm password doesn't match");
        }
        return userRepository.findByUsernameAndAccountNonLockedFalse(changePasswordRequest.getPhoneNumber())
                .map(newUser -> {
                    newUser.setPassword(hash);
                    newUser.setAccountNonLocked(false);
                    return userRepository.save(newUser);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(User) Record not found with old password: " + changePasswordRequest.getOldPassword()));
    }

    @Override
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "(User) Record with id: " + id + " has been deleted!";
    }

}
