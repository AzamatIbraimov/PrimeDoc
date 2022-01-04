package one.primedoc.backend.repository;

import one.primedoc.backend.entity.PaymentStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentStepRepository extends JpaRepository<PaymentStepEntity, Long> {
}
