package one.primedoc.backend.repository;

import one.primedoc.backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Optional<UserEntity> findByUsername(String username);

    public Optional<UserEntity> findByVerification(String token);

    public Optional<UserEntity> findByUsernameAndAccountNonLockedFalse(String password);

    public boolean existsByUsername(String username);
}
