package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.ScheduleEntity;
import one.primedoc.backend.entity.WeekEntity;
import one.primedoc.backend.exception.InvalidValueException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.ScheduleModel;
import one.primedoc.backend.repository.ScheduleRepository;
import one.primedoc.backend.service.DoctorService;
import one.primedoc.backend.service.IntervalService;
import one.primedoc.backend.service.ScheduleService;
import org.omg.CORBA.DynAnyPackage.Invalid;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final DoctorService doctorService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, DoctorService doctorService) {
        this.scheduleRepository = scheduleRepository;
        this.doctorService = doctorService;
    }

    @Override
    public List<ScheduleEntity> getAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public ScheduleEntity getById(Long id) {
        return scheduleRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Schedule) Record not found with id: " + id));
    }

    @Override
    public ScheduleEntity create(ScheduleModel schedule) {
        ScheduleEntity scheduleEntity = new ScheduleEntity();
        scheduleEntity.setCurrentWeek(schedule.getCurrentWeek());
        scheduleEntity.setDoctor(doctorService.getById(schedule.getDoctorId()));
        scheduleEntity.setWeekDuration(schedule.getWeekDuration());
        scheduleEntity.setWeeks(schedule.getWeeks());
        return scheduleRepository.save(scheduleEntity);
    }

    @Override
    public ScheduleEntity updateById(Long id, Integer currentWeek) {
        return scheduleRepository.findById(id)
                .map(newSchedule -> {
                    if (newSchedule.getWeekDuration() >= currentWeek && currentWeek > 0)
                        newSchedule.setCurrentWeek(currentWeek);
                    else
                        throw new InvalidValueException("(Schedule) Invalid value for currentWeek field");
                    return scheduleRepository.save(newSchedule);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Schedule) Record not found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {
        scheduleRepository.deleteById(id);
        return "(Schedule) Record with id: " + id + " has been deleted!";
    }

    @Override
    public ScheduleEntity getByDoctorId(Long id) {
        return scheduleRepository.findByDoctorId(id).orElseThrow(() ->
                new RecordNotFoundException("(Doctor) Record not found with id: " + id));
    }
}
