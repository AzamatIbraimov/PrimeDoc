package one.primedoc.backend.service;

import one.primedoc.backend.entity.ScheduleEntity;
import one.primedoc.backend.model.ScheduleModel;

import java.util.List;

public interface ScheduleService {
    public List<ScheduleEntity> getAll();
    public ScheduleEntity getById(Long id);
    public ScheduleEntity create(ScheduleModel schedule);
    public ScheduleEntity updateById(Long id, Integer currentWeek);
    public String deleteById(Long id);
    public ScheduleEntity getByDoctorId(Long id);
}
