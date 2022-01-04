package one.primedoc.backend.controller;

import one.primedoc.backend.entity.ScheduleEntity;
import one.primedoc.backend.model.ScheduleModel;
import one.primedoc.backend.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping("/")
    public List<ScheduleEntity> getCategories() {
        return scheduleService.getAll();
    }

    @GetMapping("/{id}")
    public ScheduleEntity getScheduleById(@PathVariable("id") Long id) {
        return scheduleService.getById(id);
    }

    @GetMapping("/doctor/{id}")
    public ScheduleEntity getScheduleByDoctorId(@PathVariable("id") Long id) {
        return scheduleService.getByDoctorId(id);
    }

    @PostMapping()
    public ScheduleEntity postSchedule(@RequestBody ScheduleModel schedule) {
        return scheduleService.create(schedule);
    }

    @PutMapping("/{id}")
    public ScheduleEntity putScheduleById(@PathVariable("id") Long id, @RequestBody Integer currentWeek) {
        return scheduleService.updateById(id, currentWeek);
    }

    @DeleteMapping("/{id}")
    public String deleteScheduleById(@PathVariable("id") Long id) {
        return scheduleService.deleteById(id);
    }
}

