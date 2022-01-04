package one.primedoc.backend.service.jwt;

import one.primedoc.backend.entity.UserEntity;
import one.primedoc.backend.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtTokenUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity loadUserByUsername(String username) {
        return userService.getByUsername(username);
    }
}
