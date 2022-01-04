package one.primedoc.backend.utils;

import one.primedoc.backend.entity.*;
import one.primedoc.backend.repository.IntervalRepository;
import one.primedoc.backend.repository.ScheduleRepository;
import one.primedoc.backend.repository.WeekDayRepository;
import one.primedoc.backend.repository.WeekRepository;
import one.primedoc.backend.service.impl.WorkTimeServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class WorkTimeScheduler {
    private final ScheduleRepository scheduleRepository;
    private final WeekRepository weekRepository;
    private final IntervalRepository intervalRepository;
    private final WorkTimeServiceImpl workTimeService;
    private final WeekDayRepository weekDayRepository;


    public WorkTimeScheduler(ScheduleRepository scheduleRepository,
                                 WeekRepository weekRepository,
                                 IntervalRepository intervalRepository,
                                 WorkTimeServiceImpl workTimeService,
                                 WeekDayRepository weekDayRepository) {
        this.scheduleRepository = scheduleRepository;
        this.weekRepository = weekRepository;
        this.intervalRepository = intervalRepository;
        this.workTimeService = workTimeService;
        this.weekDayRepository = weekDayRepository;
    }


//    @Scheduled(fixedDelay = 10000)
//    public void createWorkTimesNextWeek() {
//        ArrayList<String> timeCurrentWeekDays = new ArrayList<String>();
//        ArrayList<String> timeNextWeekDays = new ArrayList<String>();
//        workTimeService.currentTimeWeekDays(timeCurrentWeekDays, timeNextWeekDays);
//
//        List<ScheduleEntity> allSchedules = new ArrayList<ScheduleEntity>(scheduleRepository.findAll());
//
//        for (ScheduleEntity schedule : allSchedules) {
//            DoctorEntity doctor = schedule.getDoctor(); // ДОКТОР
//            Integer currentWeek = schedule.getCurrentWeek(); // НОМЕР ТЕКУЩЕЙ НЕДЕЛИ
//
//            WeekEntity currentWeekEntity = weekRepository.findByWeekOrderAndScheduleId(schedule.getId(), currentWeek);
//            // ТЕКУЩАЯ НЕДЕЛЯ
//
//            int nextWeek;
//            if (schedule.getCurrentWeek().equals(schedule.getWeekDuration())) {
//                nextWeek = 1;
//            } else {
//                nextWeek = schedule.getCurrentWeek() + 1;
//            }
//            // НАХОЖДЕНИЕ НОМЕРА ТЕКУЩЕЙ НЕДЕЛИ
//
//            WeekEntity nextWeekEntity = weekRepository.findByWeekOrderAndScheduleId(schedule.getId(), nextWeek);
//            // СЛЕДУЮЩАЯ НЕДЕЛЯ
//
//            System.out.println("НОМЕР СЛЕДУЮЩЕЙ НЕДЕЛИ: " + nextWeek);
//
//            if (timeCurrentWeekDays.get(0).equals("Monday")) {
//                WeekDayEntity weekDayEntity = weekDayRepository.findByWeekDayNameAndWeekId(currentWeekEntity.getId(), "Sunday");
//                List<IntervalEntity> intervals = intervalRepository.findAllByWeekDayId(weekDayEntity.getId());
//                Calendar c = Calendar.getInstance();
//                c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//                c.set(Calendar.HOUR_OF_DAY, 0);
//                c.set(Calendar.MINUTE, 0);
//                c.set(Calendar.SECOND, 0);
//                c.add(Calendar.DATE, 7);
//                workTimeService.createWorkTimes(intervals, c, doctor);
//            } else {
//                String lastDay = timeNextWeekDays.get(timeNextWeekDays.size() - 1);
//                System.out.println("ПОСЛЕДНИЙ ДЕНЬ СЛЕДУЮЩЕЙ НЕДЕЛИ: " + lastDay);
//                WeekDayEntity lastDayEntity = weekDayRepository.findByWeekDayNameAndWeekId(nextWeekEntity.getId(), lastDay);
//                List<IntervalEntity> intervals = intervalRepository.findAllByWeekDayId(lastDayEntity.getId());
//                Calendar c = Calendar.getInstance();
//                c.setFirstDayOfWeek(Calendar.MONDAY);
//                c.setTime(new Date());
//                int dayOFWeek = c.get(Calendar.DAY_OF_WEEK);
//                c.add(Calendar.DAY_OF_WEEK, -dayOFWeek + Calendar.MONDAY);
//                c.add(Calendar.DATE, (lastDayEntity.getId().intValue() - 1) + 7);
//                workTimeService.createWorkTimes(intervals, c, doctor);
//            }
//        }
//        System.out.println("CREATED");
//    }
}

