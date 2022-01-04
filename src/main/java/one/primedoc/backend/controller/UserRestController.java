package one.primedoc.backend.controller;

import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.firebase.FbModel;
import one.primedoc.backend.model.request.ChangePasswordRequest;
import one.primedoc.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/")
    public List<UserEntity> getCategories() { return userService.getAll(); }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @PostMapping()
    public ResponseEntity<?> postUser(@RequestBody UserEntity user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserEntity putUserById(@PathVariable("id") Long id, @RequestBody UserEntity user) {
        return userService.updateById(id, user);
    }

    @PutMapping("/")
    public UserEntity putUsersPassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.updateUsersPassword(changePasswordRequest);
    }

    @PutMapping("/fb/")
    public UserEntity putUsersPassword(@RequestBody FbModel fbModel) {
        return userService.updateByFirebaseCode(fbModel);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

}
