package one.primedoc.backend.repository;

import one.primedoc.backend.entity.statics.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQEntity, Long> {
}
