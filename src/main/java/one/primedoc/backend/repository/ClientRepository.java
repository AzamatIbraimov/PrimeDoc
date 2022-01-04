package one.primedoc.backend.repository;

import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.model.ClientModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    @Query("SELECT new one.primedoc.backend.model.ClientModel(c.id, u.username, u.firstName, u.lastName, u.patronymic, u.birthDate, c.image) FROM client c JOIN users u ON c.user.id = u.id")
    public Page<List<ClientModel>> getAllMedCards(Pageable pageable);
}
