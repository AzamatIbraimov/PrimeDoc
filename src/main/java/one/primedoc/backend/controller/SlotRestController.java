//package one.primedoc.backend.controller;
//
//import one.primedoc.backend.entity.ScheduleEntity;
//import one.primedoc.backend.entity.SlotEntity;
//import one.primedoc.backend.repository.ScheduleRepository;
//import one.primedoc.backend.service.SlotService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import static java.lang.Long.parseLong;
//
//@RestController
//@RequestMapping("/slot")
//public class SlotRestController {
//
//    private final SlotService slotService;
//
//    private final ScheduleRepository scheduleRepository;
//
//    public SlotRestController(SlotService slotService, ScheduleRepository scheduleRepository) {
//        this.slotService = slotService;
//        this.scheduleRepository = scheduleRepository;
//    }
//
//    @GetMapping("/")
//    public List<SlotEntity> getCategories() {
//        return slotService.getAll();
//    }
//
//    @GetMapping("/{id}")
//    public SlotEntity getSlotById(@PathVariable("id") Long id) {
//        return slotService.getById(id);
//    }
//
//    @GetMapping("/list/{id}")
//    public List<SlotEntity> getSlotByDoctorId(@PathVariable("id") Long id) {
//        return slotService.getAllByDoctorId(id);
//    }
//
//    @PostMapping()
//    public SlotEntity postSlot(@RequestBody SlotEntity slot) {
//        return slotService.create(slot);
//    }
//
////    @PutMapping("/{id}")
////    public SlotEntity putSlotById(@PathVariable("id") Long id, @RequestBody SlotEntity slot) {
////        return slotService.updateById(id, slot);
////    }
//
//    @DeleteMapping("/{id}")
//    public String deleteSlotById(@PathVariable("id") Long id) {
//        return slotService.deleteById(id);
//    }
//
//    @PostMapping("/create-next-week")
//    public String createSlotsForWeek(@RequestParam("scheduleId") Long scheduleId) {
//        return slotService.createSlotsForWeek(scheduleId);
//    }
//}
//
