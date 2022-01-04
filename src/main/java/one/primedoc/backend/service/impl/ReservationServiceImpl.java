package one.primedoc.backend.service.impl;

import one.primedoc.backend.dao.ReservationDao;
import one.primedoc.backend.entity.ClientEntity;
import one.primedoc.backend.entity.ReservationEntity;
import one.primedoc.backend.entity.WorkTimeEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.firebase.FcmClient;
import one.primedoc.backend.firebase.PushNotifyConf;
import one.primedoc.backend.model.BillModel;
import one.primedoc.backend.model.ReservationClientModel;
import one.primedoc.backend.model.ReservationHistoryModel;
import one.primedoc.backend.model.ReservationInfoModel;
import one.primedoc.backend.repository.ClientRepository;
import one.primedoc.backend.repository.ReservationRepository;
import one.primedoc.backend.repository.WorkTimeRepository;
import one.primedoc.backend.service.*;
//import one.primedoc.backend.service.SlotService;
import one.primedoc.backend.service.DoctorService;
import one.primedoc.backend.utils.BillGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final FcmClient fcmClient;
    private final ReservationDao reservationDao;
    private final BillGenerator billGenerator;
    private final DoctorService doctorService;
    private final ClientRepository clientRepository;
    private final WorkTimeRepository workTimeRepository;

    @Value("${bill.html.upload.path}")
    private String PATH;

    @Value("${bill.url}")
    private String BILL_URL;

    public ReservationServiceImpl(ReservationRepository reservationRepository, DoctorService doctorService, ReservationDao reservationDao, ClientRepository clientRepository, BillGenerator billGenerator, FcmClient fcmClient, WorkTimeRepository workTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.doctorService = doctorService;
        this.reservationDao = reservationDao;
        this.clientRepository = clientRepository;
        this.billGenerator = billGenerator;
        this.fcmClient = fcmClient;
        this.workTimeRepository = workTimeRepository;
    }


    @Override
    public List<ReservationEntity> getAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Page<ReservationInfoModel> getAllInfo(Pageable pageable) {
        return reservationRepository.getAllInfo(pageable);
    }

    @Override
    public List<ReservationClientModel> getReservationByWorkTimeId(Long id) {
        return reservationDao.getReservationByWorkTimeId(id);
    }

    @Override
    public ReservationEntity getById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Reservation) Record not found with id: " + id));
    }

    @Override
    public ReservationEntity create(ReservationEntity reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public ReservationEntity updateById(Long id, ReservationEntity reservation) {
        return reservationRepository.findById(id)
                .map(newReservation -> {
                    newReservation.setComment(reservation.getComment());
                    newReservation.setPhoneNumber(reservation.getPhoneNumber());
                    return reservationRepository.save(newReservation);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Reservation) Record not found with id: " + id));
    }

    @Override
    public ReservationEntity changePaidById(Long id) {
        ReservationEntity reservation = reservationRepository.getByIdAndPaidFalse(id).orElseThrow(() ->
                new RecordNotFoundException("(Reservation) Record not found with id: " + id + "and paid is false."));
        return savePaid(reservation, true);
    }

    public static LocalDate getLocalDateFromDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("Asia/Bishkek")));
    }

    @Override
    public ReservationEntity savePaid(ReservationEntity reservation, Boolean value) {
        try {
            String token = reservation.getClient().getUser().getFirebaseToken();
            if (token != null && !token.equals("")) {
                fcmClient.sendPersonal(PushNotifyConf.builder()
                        .title("Prime Doc")
                        .body("Ваша начнется через 5 минут. Будьте готовы!")
                        .icon("")
                        .scheduled(true)
                        .date(reservationDao.getReservationTime(reservation.getId()))
                        .click_action("Открыть")
                        .ttlInSeconds("60")
                        .build(), token);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        reservation.setPaid(value);
        return reservationRepository.save(reservation);
    }

    public List<ReservationHistoryModel> getMadeReservationsByClientId(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Client) Record not found with id: " + id));
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByClientAndPaid(client, true); //оплаченные брони по айди клиента
        List<ReservationHistoryModel> reservationHistoryModelList = new ArrayList<>();
        for (ReservationEntity reservation : reservationEntities) {
            Long doctorId = doctorService.getDoctorIdByReservationId(reservation.getId());
            WorkTimeEntity workTimeEntity = workTimeRepository.findByReservation(reservation);
            Date workTimeEnd = workTimeEntity.getEnd();
            LocalDateTime reservationDateTime = LocalDateTime.of(getLocalDateFromDate(workTimeEnd), reservation.getEnd().toLocalTime());
            if (LocalDateTime.now().isAfter(reservationDateTime)) {
                buildReservationModel(reservationHistoryModelList, reservation, doctorId);
            }
        }
        return reservationHistoryModelList;
    }

    @Override
    public List<ReservationHistoryModel> getCurrentReservationsByClientId(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Client) Record not found with id: " + id));
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByClientAndPaid(client, false); //оплаченные брони по айди клиента
        List<ReservationHistoryModel> reservationHistoryModelList = new ArrayList<>();
        for (ReservationEntity reservation : reservationEntities) {
            Long doctorId = doctorService.getDoctorIdByReservationId(reservation.getId());
            WorkTimeEntity workTimeEntity = workTimeRepository.findByReservation(reservation);
            Date workTimeEnd = workTimeEntity.getEnd();
            LocalDateTime reservationDateTime = LocalDateTime.of(getLocalDateFromDate(workTimeEnd), reservation.getEnd().toLocalTime());
            if (LocalDateTime.now().isBefore(reservationDateTime)) {
                buildReservationModel(reservationHistoryModelList, reservation, doctorId);
            }
        }
        return reservationHistoryModelList;
    }

    private void buildReservationModel(List<ReservationHistoryModel> reservationHistoryModelList, ReservationEntity reservation, Long doctorId) {
        if (doctorId == null) {
            ReservationHistoryModel reservationHistoryModel = ReservationHistoryModel.builder()
                    .id(reservation.getId())
                    .checkUrl(reservation.getBill())
                    .start(findReservationDate(reservation))
                    .end(reservation.getEnd())
                    .phoneNumber(reservation.getPhoneNumber())
                    .build();
            reservationHistoryModelList.add(reservationHistoryModel);
        } else {
            ReservationHistoryModel reservationHistoryModel = ReservationHistoryModel.builder()
                    .id(reservation.getId())
                    .checkUrl(reservation.getBill())
                    .start(findReservationDate(reservation))
                    .end(reservation.getEnd())
                    .doctor(doctorService.getDoctorDataById(doctorId))
                    .phoneNumber(reservation.getPhoneNumber())
                    .build();
            reservationHistoryModelList.add(reservationHistoryModel);
        }
    }

    public String findReservationDate(ReservationEntity reservation) {
        Date workTimeStartDateTime = workTimeRepository.findByReservation(reservation).getStart();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dateWithoutTimeStr = sdf.format(workTimeStartDateTime);
        String dateTime = dateWithoutTimeStr + " " + reservation.getStart();
        System.out.println(dateTime);
        return dateTime;
    }

    @Override
    public List<ReservationHistoryModel> getApprovedReservationsByClientId(Long id) {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(Client) Record not found with id: " + id));
        List<ReservationEntity> reservationEntities = reservationRepository.findAllByClientAndPaid(client, true); //оплаченные брони по айди клиента
        List<ReservationHistoryModel> reservationHistoryModelList = new ArrayList<>();
        for (ReservationEntity reservation : reservationEntities) {
            Long doctorId = doctorService.getDoctorIdByReservationId(reservation.getId());
            WorkTimeEntity workTimeEntity = workTimeRepository.findByReservation(reservation);
            Date workTimeEnd = workTimeEntity.getEnd();
            LocalDateTime reservationDateTime = LocalDateTime.of(getLocalDateFromDate(workTimeEnd), reservation.getEnd().toLocalTime());
            if (LocalDateTime.now().isBefore(reservationDateTime)) {
                buildReservationModel(reservationHistoryModelList, reservation, doctorId);
            }
        }
        return reservationHistoryModelList;
    }

    @Override
    public ReservationEntity payReservationById(Long id, BillModel model) {
        ReservationEntity reservation = reservationRepository.getByIdAndPaidFalse(id).orElseThrow(() ->
                new RecordNotFoundException("(Reservation) Record not found with id: " + id + "and paid is false."));
        reservation.setBill(saveBillHtml(billGenerator.generateHtmlBill(model), reservation));
        return savePaid(reservation, true);
    }


    @Override
    public String deleteById(Long id) {
        reservationRepository.deleteById(id);
        return "(Reservation) Record with id: " + id + " has been deleted!";
    }

    @Override
    public Boolean isExistWithSuchStartAndEndAndWorkTimeId(Long id, Time start, Time end) {
        return reservationRepository.isDuplicateReservation(id, start, end);
    }
//
//    @Override
//    public ReservationEntity reserveSlot(ReservationModel reservation) {
//
//        return reservationRepository.save(ReservationEntity.builder()
//                .phoneNumber(reservation.getPhoneNumber())
//                .comment(reservation.getComment())
//                .client(clientService.getById(reservation.getClientId().getId()))
//                .paid(false)
//                .build()
//        );
//    }

    public String saveBillHtml(String html, ReservationEntity reservation) {
        BufferedWriter bufferedWriter = null;
        String filename = "bill_" + reservation.getId();
        System.out.println("TEST");
        try {
            File myFile = new File(PATH + filename + ".html");
            if (!myFile.exists()) {
                myFile.createNewFile();
            }
            Writer writer = new FileWriter(myFile);
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(html);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return BILL_URL + filename + ".html";
    }
}
