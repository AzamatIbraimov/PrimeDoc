package one.primedoc.backend.repository;

import one.primedoc.backend.entity.DoctorEntity;
import one.primedoc.backend.model.DoctorDataModel;
import one.primedoc.backend.model.DoctorPersonalInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
    @Query(value = "SELECT d.id, u.username, u.firstName, u.lastName, u.patronymic, d.position, c.id as catId, c.name FROM doctor d JOIN users u on u.id = d.user_id JOIN doctor_category dc on d.id = dc.doctor_id JOIN category c on c.id = dc.category_id", nativeQuery = true)
    public List<?> findAllDoctorsData();
    @Query(value = "SELECT ", nativeQuery = true)
    public DoctorEntity findByWorkTimeId();
}
