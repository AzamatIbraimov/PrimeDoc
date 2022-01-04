package one.primedoc.backend.controller;


import one.primedoc.backend.entity.DoctorEntity;
import one.primedoc.backend.model.*;
import one.primedoc.backend.service.DoctorService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorRestController {

    private final DoctorService doctorService;

    public DoctorRestController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/")
    public List<DoctorEntity> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/data/")
    public List<DoctorPersonalInfoModel> getAllDoctorsData() {
        return doctorService.getAllDoctorsData();
    }

    @GetMapping("/{id}")
    public DoctorEntity getDoctorById(@PathVariable("id") Long id) {
        return doctorService.getById(id);
    }

    @GetMapping("/full/{id}")
    public DoctorFullInfoModel getDoctorAllInfoById(@PathVariable("id") Long id) {
        return doctorService.getDoctorFullInfoById(id);
    }

    @GetMapping("/profile/{id}")
    public DoctorDetailsModel getDoctorFullInfoById(@PathVariable("id") Long id) {
        return doctorService.getDoctorDetails(id);
    }

    @PostMapping()
    public DoctorCreatedModel postDoctor(@RequestPart("doctor") DoctorModel doctor, @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        return doctorService.create(doctor, imageFile);
    }

    @PutMapping("/{id}")
    public DoctorEntity putDoctorById(@PathVariable("id") Long id, @RequestBody DoctorDataChangeModel doctor) {
        return doctorService.updateById(id, doctor);
    }

    @PutMapping("/image/{id}")
    public DoctorEntity putDoctorImageById(@PathVariable("id") Long id, @ModelAttribute("imageFile") MultipartFile imageFile) {
        return doctorService.updateImageById(id, imageFile);
    }

    @PostMapping("/deactivate/{id}")
    public String deactivateDoctorById(@PathVariable("id") Long id) {
        return doctorService.deactivateById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteDoctorById(@PathVariable("id") Long id) {
        return doctorService.deleteById(id);
    }

}
