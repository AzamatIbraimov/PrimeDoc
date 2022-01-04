package one.primedoc.backend.controller;

import io.swagger.annotations.Api;
import one.primedoc.backend.entity.DoctorInformationEntity;
import one.primedoc.backend.service.DoctorInformationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctor/info")
public class DoctorInformationRestController {
    private final DoctorInformationService doctorInfoService;

    public DoctorInformationRestController(DoctorInformationService doctorInfoService) {
        this.doctorInfoService = doctorInfoService;
    }

    @GetMapping("/{id}")
    public DoctorInformationEntity getDoctorInformationById(@PathVariable("id") Long id) {
        return doctorInfoService.getById(id);
    }

    @PostMapping()
    public DoctorInformationEntity postDoctorInformation(@RequestBody DoctorInformationEntity doctorInfo) {
        return doctorInfoService.create(doctorInfo);
    }

    @PutMapping("/{id}")
    public DoctorInformationEntity putDoctorInformationById(@PathVariable("id") Long id, @RequestBody DoctorInformationEntity doctorInfo) {
        return doctorInfoService.updateById(id, doctorInfo);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctorInformationById(@PathVariable("id") Long id) {
        return doctorInfoService.deleteById(id);
    }

}
