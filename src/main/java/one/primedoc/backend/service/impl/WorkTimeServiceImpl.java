package one.primedoc.backend.service.impl;


import one.primedoc.backend.entity.*;
import one.primedoc.backend.exception.DuplicateException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.ReservationTimeException;
import one.primedoc.backend.model.ReservationModel;
import one.primedoc.backend.repository.*;
import one.primedoc.backend.service.ClientService;
import one.primedoc.backend.service.ReservationService;
import one.primedoc.backend.service.WorkTimeService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class WorkTimeServiceImpl implements WorkTimeService {
    private final WorkTimeRepository workTimeRepository;
    private final ScheduleRepository scheduleRepository;
    private final WeekDayRepository weekDayRepository;
    private final WeekRepository weekRepository;
    private final IntervalRepository intervalRepository;
    private final ClientService clientService;
    private final ReservationService reservationService;

    private final String[] weekDayList = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};


    public WorkTimeServiceImpl(WorkTimeRepository workTimeRepository,
                               ScheduleRepository scheduleRepository,
                               WeekDayRepository weekDayRepository,
                               WeekRepository weekRepository,
                               IntervalRepository intervalRepository,
                               ClientService clientService,
                               ReservationService reservationService) {
        this.workTimeRepository = workTimeRepository;
        this.scheduleRepository = scheduleRepository;
        this.weekDayRepository = weekDayRepository;
        this.weekRepository = weekRepository;
        this.intervalRepository = intervalRepository;
        this.clientService = clientService;
        this.reservationService = reservationService;
    }

    @Override
    public List<WorkTimeEntity> getAll() {
        return workTimeRepository.findAll();
    }

    @Override
    public WorkTimeEntity getById(Long id) {
        return workTimeRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(WorkTime) Record not found with id: " + id));
    }

    @Override
    public WorkTimeEntity create(WorkTimeEntity workTime) {
        return workTimeRepository.save(workTime);
    }

    @Override
    public String deleteById(Long id) {
        workTimeRepository.deleteById(id);
        return "(WorkTime) Record with id: " + id + " has been deleted!";
    }

    @Override
    public WorkTimeEntity reserve(ReservationModel reservation) {
        WorkTimeEntity workTimeEntity = workTimeRepository.findById(reservation.getWorktimeId()).orElseThrow(() ->
                new RecordNotFoundException("(WorkTime) Record not found with id: " + reservation.getWorktimeId()));
        LocalTime workStart = LocalDateTime.parse(workTimeEntity.getStart().toInstant().toString().replace("Z", "")).toLocalTime();
        LocalTime workEnd = LocalDateTime.parse(workTimeEntity.getEnd().toInstant().toString().replace("Z", "")).toLocalTime();
        LocalTime reservationStart = LocalTime.parse(reservation.getStart().toString());
        LocalTime reservationEnd = LocalTime.parse(reservation.getEnd().toString());
        ReservationEntity reservationEntity = ReservationEntity.builder()
                .phoneNumber(reservation.getPhoneNumber())
                .comment(reservation.getComment())
                .paid(false)
                .start(reservation.getStart())
                .end(reservation.getEnd())
                .client(clientService.getById(reservation.getClientId()))
                .build();
        if ((((reservationStart.isAfter(workStart) || reservationStart.equals(workStart)) && (reservationEnd.isBefore(workEnd) || reservationEnd.equals(workEnd)))
                &&
                reservationStart.isBefore(reservationEnd)
                &&
                reservationStart.plus(30, ChronoUnit.MINUTES).equals(reservationEnd))) {
            if (!reservationService.isExistWithSuchStartAndEndAndWorkTimeId(workTimeEntity.getId(), reservation.getStart(), reservation.getEnd())) {
                workTimeEntity.getReservation().add(reservationEntity);
                return workTimeRepository.save(workTimeEntity);
            } else {
                throw new DuplicateException("(Reservation) Record with such start or end value already exists for WorkTime record with id: " + workTimeEntity.getId());
            }
        } else {
            throw new ReservationTimeException("(Reservation) Invalid start or end value for WorkTime record with id: " + reservation.getWorktimeId());
        }
    }

    public static LocalDate getLocalDateFromDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneOffset.UTC));
    }

    @Override
    public List<WorkTimeEntity> getRelevantSlotsByDoctorId(Long id) {
        List<WorkTimeEntity> workTimes = workTimeRepository.findAllByDoctor_IdAndStartLessThan(id);
        for (WorkTimeEntity workTime : workTimes) {
            List<ReservationEntity> reservations = workTime.getReservation();
            List<ReservationEntity> emptyReservations = new ArrayList<>();
            LocalTime time = LocalTime.parse(workTime.getStart().toInstant().toString().substring(11).replace("Z", ""));
            LocalTime limit = LocalTime.parse(workTime.getEnd().toInstant().toString().substring(11).replace("Z", ""));
            if (limit.equals(LocalTime.of(0,0))) {
                limit = LocalTime.MAX;
            }
            if (reservations.isEmpty()) {
                for (; time.isBefore(limit); time = time.plusMinutes(30)) {
                    reservations.add(ReservationEntity.builder().start(Time.valueOf(time)).end(Time.valueOf(time.plusMinutes(30))).paid(false).build());
                    if (time.equals(LocalTime.of(23, 30))) {
                        break;
                    }
                }
            } else {

                for (; time.isBefore(limit); time = time.plusMinutes(30)) {
                    boolean isPresent = false;
                    ReservationEntity reservation = ReservationEntity.builder().start(Time.valueOf(time)).end(Time.valueOf(time.plusMinutes(30))).paid(false).build();
                    for (ReservationEntity real : reservations) {
                        if (real.getStart().equals(reservation.getStart())) {
                            isPresent = true;
                            break;
                        }
                    }
                    if (!isPresent) {
                        emptyReservations.add(reservation);
                        if (time.equals(LocalTime.of(23, 30))) {
                            break;
                        }
                    }
                }
                reservations.addAll(emptyReservations);
            }
        }
        return workTimes;
    }

    @Override
    public String createWorkTimesForWeek(Long scheduleId) {

        ScheduleEntity schedule = scheduleRepository.findById(scheduleId).orElseThrow(() ->
                new RecordNotFoundException("(Schedule) Record not found with id: " + scheduleId));

        if (schedule.getIsGenerated() == false) {
            DoctorEntity doctor = schedule.getDoctor();
            Integer currentWeek = schedule.getCurrentWeek();

            int nextWeek;
            // СЛЕДУЮЩИЙ ОРДЕР НЕДЕЛИ
            if (schedule.getCurrentWeek().equals(schedule.getWeekDuration())) {
                nextWeek = 1;
            } else {
                nextWeek = schedule.getCurrentWeek() + 1;
            }
            WeekEntity currentWeekEntity = weekRepository.findByWeekOrderAndScheduleId(schedule.getId(), currentWeek);
            // ТЕКУЩАЯ НЕДЕЛЯ ПО ОРДЕРУ

            WeekEntity nextWeekEntity = weekRepository.findByWeekOrderAndScheduleId(schedule.getId(), nextWeek);
            // СЛЕДУЮЩАЯ НЕДЕЛЯ ПО ОРДЕРУ

            List<WeekDayEntity> currentWeekdays = weekDayRepository.findByWeekId(currentWeekEntity.getId());
            // СПИСОК ТЕКУЩИХ ВИДЭЕВ

            List<WeekDayEntity> nextWeekdays = weekDayRepository.findByWeekId(nextWeekEntity.getId());
            // СПИСОК СЛЕДУЮЩИХ ВИКДЭЕВ

            ArrayList<String> timeCurrentWeekDays = new ArrayList<String>();
            // СПИСОК ТЕКУЩИХ ДНЕЙ НЕДЕЛИ ДО ВОСКРЕСЕНЬЯ
            ArrayList<String> timeNextWeekDays = new ArrayList<String>();
            // СПИСОК СЛЕДУЮЩИХ ДНЕЙ НЕДЕЛИ ПОСЛЕ ВОСКРЕСЕНЬЯ

            currentTimeWeekDays(timeCurrentWeekDays, timeNextWeekDays);

            for (WeekDayEntity weekDayEntity : currentWeekdays) {
                if (timeCurrentWeekDays.contains(weekDayEntity.getWeekDayName())) {
                    List<IntervalEntity> intervals = intervalRepository.findAllByWeekDayId(weekDayEntity.getId());
                    Calendar c = Calendar.getInstance();
                    c.setFirstDayOfWeek(Calendar.MONDAY);
                    c.setTime(new Date());
                    int dayOFWeek = c.get(Calendar.DAY_OF_WEEK);
                    c.add(Calendar.DAY_OF_WEEK, -dayOFWeek + Calendar.MONDAY);
                    String weekDayName = weekDayEntity.getWeekDayName();
                    Integer number = null;
                    for (int weekDayNumber = 0; weekDayNumber <= weekDayList.length - 1; weekDayNumber++) {
                        if (weekDayList[weekDayNumber].equals(weekDayName)) number = weekDayNumber;
                    }
                    c.add(Calendar.DATE, number);
                    createWorkTimes(intervals, c, doctor);
                }
            }

            for (WeekDayEntity nextWeekDayEntity : nextWeekdays) {
                if (timeNextWeekDays.contains(nextWeekDayEntity.getWeekDayName())) {
                    List<IntervalEntity> intervals = intervalRepository.findAllByWeekDayId(nextWeekDayEntity.getId());
                    Calendar c = Calendar.getInstance();
                    c.setFirstDayOfWeek(Calendar.MONDAY);
                    c.setTime(new Date());
                    int dayOFWeek = c.get(Calendar.DAY_OF_WEEK);
                    c.add(Calendar.DAY_OF_WEEK, -dayOFWeek + Calendar.MONDAY);
                    String weekDayName = nextWeekDayEntity.getWeekDayName();
                    Integer number = null;
                    for (int weekDayNumber = 0; weekDayNumber <= weekDayList.length - 1; weekDayNumber++) {
                        if (weekDayList[weekDayNumber].equals(weekDayName)) number = weekDayNumber;
                    }
                    if (isTodaySunday()) {
                        c.add(Calendar.DATE, number);
                    } else {
                        c.add(Calendar.DATE, number + 7);
                    }
                    createWorkTimes(intervals, c, doctor);
                }
            }
            ;
            schedule.setIsGenerated(true);
            scheduleRepository.save(schedule);
            return "WorkTimes created successfully";

        } else {
            return "Already generated for schedule " + schedule.getId();
        }
    }

    public boolean isTodaySunday() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, 0);
        Date newDay = cal.getTime();
        SimpleDateFormat weekDay = new SimpleDateFormat("EEEE");
        String weekDayName = weekDay.format(newDay);
        return weekDayName.equals("Sunday");
    }

    public void currentTimeWeekDays(List<String> timeCurrentWeekDays, List<String> timeNextWeekDays) {
        Date today = new Date();

        for (int i = 0; i <= 6; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.DATE, i);
            Date newDay = cal.getTime();
            SimpleDateFormat weekDay = new SimpleDateFormat("EEEE");
            String weekDayName = weekDay.format(newDay);
            if (weekDayName.equals("Sunday")) {
                timeCurrentWeekDays.add(weekDayName);
                for (int j = i + 1; j <= 6; j++) {
                    Calendar calNext = Calendar.getInstance();
                    calNext.setTime(today);
                    calNext.add(Calendar.DATE, j);
                    Date newDayNext = calNext.getTime();
                    SimpleDateFormat weekDayNext = new SimpleDateFormat("EEEE");
                    String weekDayNameNext = weekDayNext.format(newDayNext);
                    timeNextWeekDays.add(weekDayNameNext);
                }
                break;
            }
            timeCurrentWeekDays.add(weekDayName);
        }
    }

    private static Date copyTimeToDate(Date date, Date time) {
        Calendar t = Calendar.getInstance();
        t.setTime(time);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
        c.set(Calendar.SECOND, t.get(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));
        return c.getTime();
    }

    public void createWorkTimes(List<IntervalEntity> intervals, Calendar c, DoctorEntity doctor) {
        for (IntervalEntity i : intervals) {
            if (i.getEnd().before(i.getStart())) {
                Date intervalDate = c.getTime();
                Calendar prevCalendar = Calendar.getInstance();
                prevCalendar.setTime(intervalDate);
                prevCalendar.add(Calendar.DATE, -1);
                Date prevDayDate = prevCalendar.getTime();


                Date firstSpecialIntervalStart = i.getStart();
                Date firstSpecialIntervalEnd = new Time(Time.valueOf("00:00:00").getTime());

                Calendar start = Calendar.getInstance();
                start.setTime(firstSpecialIntervalStart);

                Calendar end = Calendar.getInstance();
                end.setTime(firstSpecialIntervalEnd);

                WorkTimeEntity workTime = new WorkTimeEntity();
                workTime.setStart(copyTimeToDate(prevDayDate, firstSpecialIntervalStart));
                workTime.setEnd(copyTimeToDate(intervalDate, firstSpecialIntervalEnd));
                workTime.setDoctor(doctor);
                workTimeRepository.save(workTime);

                Date secondSpecialIntervalStart = new Time(Time.valueOf("00:00:00").getTime());
                Date secondSpecialIntervalEnd = i.getEnd();

                Calendar secondStart = Calendar.getInstance();
                secondStart.setTime(secondSpecialIntervalStart);

                Calendar secondEnd = Calendar.getInstance();
                secondEnd.setTime(secondSpecialIntervalEnd);

                WorkTimeEntity secondWorkTime = new WorkTimeEntity();
                secondWorkTime.setStart(copyTimeToDate(intervalDate, secondSpecialIntervalStart));
                secondWorkTime.setEnd(copyTimeToDate(intervalDate, secondSpecialIntervalEnd));
                secondWorkTime.setDoctor(doctor);
                workTimeRepository.save(secondWorkTime);
            } else {
                Date intervalDate = c.getTime();
                // дата интервала
                Date intervalStart = i.getStart();
                // время начала интервала
                Date intervalEnd = i.getEnd();
                // время окончания интервала
                Calendar start = Calendar.getInstance();
                start.setTime(intervalStart);
                // время начала интервала в календаре
                Calendar end = Calendar.getInstance();
                end.setTime(intervalEnd);
                // время окончания интервала в календаре
                WorkTimeEntity workTime = new WorkTimeEntity();
                // создание ворктайма
                workTime.setStart(copyTimeToDate(intervalDate, intervalStart));
                // фулл дейттайм сетится в ворктайм
                workTime.setEnd(copyTimeToDate(intervalDate, intervalEnd));
                workTime.setDoctor(doctor);
                workTimeRepository.save(workTime);
            }

        }
    }
}
