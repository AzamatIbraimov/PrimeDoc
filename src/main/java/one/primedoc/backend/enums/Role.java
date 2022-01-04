package one.primedoc.backend.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN,
    CUSTOMER,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
