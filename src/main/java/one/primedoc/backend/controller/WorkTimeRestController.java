package one.primedoc.backend.controller;


import one.primedoc.backend.entity.WorkTimeEntity;
//import one.primedoc.backend.entity.SlotEntity;
import one.primedoc.backend.model.ReservationModel;
import one.primedoc.backend.service.IntervalService;
import one.primedoc.backend.service.WorkTimeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/worktime")
public class WorkTimeRestController {

    private final WorkTimeService workTimeService;

    public WorkTimeRestController(WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
    }

    @GetMapping("/")
    public List<WorkTimeEntity> getCategories() {
        return workTimeService.getAll();
    }

    @GetMapping("/{id}")
    public WorkTimeEntity getSlotById(@PathVariable("id") Long id) {
        return workTimeService.getById(id);
    }

    @GetMapping("/relevant/{doctorId}")
    public List<WorkTimeEntity> getRelevantSlotsByDoctorId(@PathVariable("doctorId") Long id) {
        return workTimeService.getRelevantSlotsByDoctorId(id);
    }

    @PostMapping()
    public WorkTimeEntity postSlot(@RequestBody WorkTimeEntity workTime) {
        return workTimeService.create(workTime);
    }

    @PutMapping("/reserve")
    public WorkTimeEntity reserve(@RequestBody ReservationModel reservation) {
        return workTimeService.reserve(reservation);
    }

    @DeleteMapping("/{id}")
    public String deleteWorkTimeById(@PathVariable("id") Long id) {
        return workTimeService.deleteById(id);
    }

    @PostMapping("/generate/{scheduleId}")
    public String createWorkTimesForWeek(@PathVariable("scheduleId") Long scheduleId) {
        return workTimeService.createWorkTimesForWeek(scheduleId);
    }
}
