package one.primedoc.backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import one.primedoc.backend.entity.statics.BaseEntity;
import one.primedoc.backend.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(name = "users")
public class UserEntity extends BaseEntity implements UserDetails {
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;
    //    @Email
//    @Column(name = "email", length = 50)
//    private String email;
    @Column(name = "firstname", length = 50)
    private String firstName;
    @Column(name = "lastname", length = 50)
    private String lastName;
    @Column(name = "patronymic", length = 50)
    private String patronymic;
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "verification")
    private String verification;
    @Column(name = "firebase_token")
    private String firebaseToken;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> authorities;
    @Column(name = "account_non_expired", nullable = false, columnDefinition = "bool default 't'")
    private Boolean accountNonExpired;
    @Column(name = "account_non_locked", nullable = false, columnDefinition = "bool default 't'")
    private Boolean accountNonLocked;
    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "bool default 't'")
    private Boolean credentialsNonExpired;
    @Column(name = "enabled", nullable = false, columnDefinition = "bool default 'f'")
    private Boolean enabled;

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
