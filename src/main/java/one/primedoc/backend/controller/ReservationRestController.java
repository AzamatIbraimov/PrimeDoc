package one.primedoc.backend.controller;

import one.primedoc.backend.dao.ReservationDao;
import one.primedoc.backend.entity.ReservationEntity;
import one.primedoc.backend.enums.ResultCode;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.BillModel;
import one.primedoc.backend.model.ReservationClientModel;
import one.primedoc.backend.model.ReservationHistoryModel;
import one.primedoc.backend.model.ReservationInfoModel;
import one.primedoc.backend.model.ResponseMessage;
import one.primedoc.backend.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
public class ReservationRestController {

    private final ReservationService reservationService;
    private final ReservationDao dao;

    public ReservationRestController(ReservationService reservationService, ReservationDao dao) {
        this.reservationService = reservationService;
        this.dao = dao;
    }

    @GetMapping("/")
    public List<ReservationEntity> getReservations() {
        return reservationService.getAll();
    }

    @GetMapping("/{id}")
    public ReservationEntity getReservationById(@PathVariable("id") Long id) {
        return reservationService.getById(id);
    }

    @GetMapping("/info")
    public Page<ReservationInfoModel> getReservationsInfo(@RequestParam("page") Integer page, @RequestParam(value = "size", defaultValue = "15") Integer size) {
        return reservationService.getAllInfo(PageRequest.of(page, size));
    }

    @GetMapping("/worktime/{id}")
    public List<ReservationClientModel> getReservationByWorkTimeId(@PathVariable("id") Long id) {
        return reservationService.getReservationByWorkTimeId(id);
    }

    @GetMapping("/made/{id}")
    public List<ReservationHistoryModel> getMadeReservationsByClientId(@PathVariable("id") Long id) {
        return reservationService.getMadeReservationsByClientId(id);
    }

    @GetMapping("/current/{id}")
    public List<ReservationHistoryModel> getCurrentReservationsByClientId(@PathVariable("id") Long id) {
        return reservationService.getCurrentReservationsByClientId(id);
    }

    @GetMapping("/approved/{id}")
    public List<ReservationHistoryModel> getApprovedReservationsByClientId(@PathVariable("id") Long id) {
        return reservationService.getApprovedReservationsByClientId(id);
    }

    @GetMapping("/time/{id}")
    public String temp(@PathVariable("id") Long id) {
        return dao.getReservationTime(id);
    }

//    @PostMapping("/")
//    public ReservationEntity postReservation(@RequestBody ReservationEntity reservation) { return reservationService.reserveSlot(reservation); }

    @PutMapping("/{id}")
    public ReservationEntity putReservationById(@PathVariable("id") Long id, @RequestBody ReservationEntity reservation) {
        return reservationService.updateById(id, reservation);
    }

    @PutMapping("/approve/{id}")
    public ReservationEntity putReservationById(@PathVariable("id") Long id) {
        return reservationService.changePaidById(id);
    }

    @PutMapping("/pay/{id}")
    public ReservationEntity payReservationById(@PathVariable("id") Long id, @RequestBody BillModel model) {
        return reservationService.payReservationById(id, model);
    }

    @DeleteMapping("/{id}")
    public String deleteReservationById(@PathVariable("id") Long id) {
        return reservationService.deleteById(id);
    }
}

