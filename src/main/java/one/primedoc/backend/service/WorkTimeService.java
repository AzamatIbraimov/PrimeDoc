package one.primedoc.backend.service;

import one.primedoc.backend.entity.WorkTimeEntity;
import one.primedoc.backend.model.ReservationModel;

import java.util.List;

public interface WorkTimeService {
    public List<WorkTimeEntity> getAll();
    public WorkTimeEntity getById(Long id);
    public String createWorkTimesForWeek(Long scheduleId);
    public WorkTimeEntity create(WorkTimeEntity workTime);
    public String deleteById(Long id);
    public WorkTimeEntity reserve(ReservationModel reservation);
    public List<WorkTimeEntity> getRelevantSlotsByDoctorId(Long id);
}
