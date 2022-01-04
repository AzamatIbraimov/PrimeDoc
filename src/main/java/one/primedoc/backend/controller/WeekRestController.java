package one.primedoc.backend.controller;

import one.primedoc.backend.entity.WeekEntity;
import one.primedoc.backend.service.WeekService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/week")
public class WeekRestController {

    private final WeekService weekService;

    public WeekRestController(WeekService weekService) {
        this.weekService = weekService;
    }

    @GetMapping("/")
    public List<WeekEntity> getCategories() { return weekService.getAll(); }

    @GetMapping("/{id}")
    public WeekEntity getWeekById(@PathVariable("id") Long id) {
        return weekService.getById(id);
    }

    @PostMapping()
    public WeekEntity postWeek(@RequestBody WeekEntity week) { return weekService.create(week); }

    @PutMapping("/{id}")
    public WeekEntity putWeekById(@PathVariable("id") Long id, @RequestBody WeekEntity week) {
        return weekService.updateById(id, week);
    }

    @DeleteMapping("/{id}")
    public String deleteWeekById(@PathVariable("id") Long id) {
        return weekService.deleteById(id);
    }
}
