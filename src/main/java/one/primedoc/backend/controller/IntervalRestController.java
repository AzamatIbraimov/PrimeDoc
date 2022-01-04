package one.primedoc.backend.controller;

import one.primedoc.backend.entity.IntervalEntity;
import one.primedoc.backend.service.IntervalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interval")
public class IntervalRestController {

    private final IntervalService intervalService;

    public IntervalRestController(IntervalService intervalService) {
        this.intervalService = intervalService;
    }

    @GetMapping("/")
    public List<IntervalEntity> getCategories() { return intervalService.getAll(); }

    @GetMapping("/{id}")
    public IntervalEntity getIntervalById(@PathVariable("id") Long id) {
        return intervalService.getById(id);
    }

    @PostMapping()
    public IntervalEntity postInterval(@RequestBody IntervalEntity interval) { return intervalService.create(interval); }

    @PutMapping("/{id}")
    public IntervalEntity putIntervalById(@PathVariable("id") Long id, @RequestBody IntervalEntity interval) {
        return intervalService.updateById(id, interval);
    }

    @DeleteMapping("/{id}")
    public String deleteIntervalById(@PathVariable("id") Long id) {
        return intervalService.deleteById(id);
    }
}
