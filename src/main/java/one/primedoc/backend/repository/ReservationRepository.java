package one.primedoc.backend.repository;

import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.entity.ReservationEntity;
import one.primedoc.backend.model.ReservationInfoModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    @Query("SELECT CASE WHEN (COUNT(r) > 0) THEN TRUE ELSE FALSE END FROM reservation r JOIN work_time wt ON wt.id = :id WHERE r MEMBER OF wt.reservation AND r.start = :start AND r.end = :end")
    Boolean isDuplicateReservation(Long id, Time start, Time end);
    Optional<ReservationEntity> getByIdAndPaidFalse(Long id);
    @Query("SELECT new one.primedoc.backend.model.ReservationInfoModel(r.id, r.client.id, wt.doctor.id, c.user.id, wt.doctor.user.id, u.firstName, u.lastName, u.patronymic, r.phoneNumber, wt.start , r.start, r.end, r.paid, wt.doctor.user.firstName, wt.doctor.user.lastName, wt.doctor.user.patronymic) FROM reservation r JOIN client c on r.client.id = c.id JOIN users u on c.user.id = u.id JOIN work_time wt ON r member of wt.reservation WHERE r.paid = FALSE ")
    Page<ReservationInfoModel> getAllInfo(Pageable pageable);
    List<ReservationEntity> findAllByClientAndPaid(@NotNull ClientEntity client, @NotNull boolean isPaid);
}
