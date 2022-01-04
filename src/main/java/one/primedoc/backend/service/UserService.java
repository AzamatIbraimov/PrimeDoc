package one.primedoc.backend.service;

import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.enums.Role;
import one.primedoc.backend.firebase.FbModel;
import one.primedoc.backend.model.request.ChangePasswordRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public List<UserEntity> getAll();

    public UserEntity getById(Long id);

    public ResponseEntity<?> create(UserEntity user);

    public UserEntity updateById(Long id, UserEntity user);

    public String deleteById(Long id);

    public UserEntity getByUsername(String username);

    public UserEntity updateByVerification(String token);

    public UserEntity updateByRecovery(String phone, String password);

    public UserEntity updateVerification(String phone, String code);

    public Boolean existsByUsername(String username);

    public UserEntity updateUsersPassword(ChangePasswordRequest changePasswordRequest);

    public Boolean isUsernameReserved(String username);

    public UserEntity updateByFirebaseCode(FbModel code);
}
