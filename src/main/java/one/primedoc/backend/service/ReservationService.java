package one.primedoc.backend.service;

import one.primedoc.backend.entity.ReservationEntity;
import one.primedoc.backend.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Time;
import java.util.List;

public interface ReservationService {
    public List<ReservationEntity> getAll();

    Page<ReservationInfoModel> getAllInfo(Pageable pageable);

    public List<ReservationClientModel> getReservationByWorkTimeId(Long id);
    public ReservationEntity getById(Long id);
    public ReservationEntity create(ReservationEntity reservation);
    public ReservationEntity updateById(Long id, ReservationEntity reservation);
    public String deleteById(Long id);
    public Boolean isExistWithSuchStartAndEndAndWorkTimeId(Long id, Time start, Time end);
//    public ReservationEntity reserveSlot(ReservationEntity reservationModel);
    public ReservationEntity changePaidById(Long id);
    public List<ReservationHistoryModel> getMadeReservationsByClientId(Long id);
    public List<ReservationHistoryModel> getCurrentReservationsByClientId(Long id);
    public List<ReservationHistoryModel> getApprovedReservationsByClientId(Long id);
    public ReservationEntity payReservationById(Long id, BillModel model);
    public ReservationEntity savePaid(ReservationEntity reservation, Boolean value);
}
