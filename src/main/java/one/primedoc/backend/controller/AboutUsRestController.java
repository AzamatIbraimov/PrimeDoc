package one.primedoc.backend.controller;

import io.swagger.annotations.Api;
import one.primedoc.backend.entity.statics.AboutUsEntity;
import one.primedoc.backend.service.AboutUsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/about")
public class AboutUsRestController {

    private final AboutUsService aboutUsService;

    public AboutUsRestController(AboutUsService aboutUsService) {
        this.aboutUsService = aboutUsService;
    }
    
    @GetMapping("/")
    public List<AboutUsEntity> getCategories() { return aboutUsService.getAll(); }

    @GetMapping("/{id}")
    public AboutUsEntity getAboutUsById(@PathVariable("id") Long id) {
        return aboutUsService.getById(id);
    }

    @PostMapping()
    public AboutUsEntity postAboutUs(@RequestBody AboutUsEntity aboutUs) {
        return aboutUsService.create(aboutUs);
    }

    @PutMapping("/{id}")
    public AboutUsEntity putAboutUsById(@PathVariable("id") Long id, @RequestBody AboutUsEntity aboutUs) {
        return aboutUsService.updateById(id, aboutUs);
    }

    @DeleteMapping("/{id}")
    public String deleteAboutUsById(@PathVariable("id") Long id) {
        return aboutUsService.deleteById(id);
    }

}
