package one.primedoc.backend.repository;

import one.primedoc.backend.entity.statics.AboutUsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AboutUsRepository extends JpaRepository<AboutUsEntity, Long> {
}
