package one.primedoc.backend.controller;

import one.primedoc.backend.entity.statics.FAQEntity;
import one.primedoc.backend.model.OrderModel;
import one.primedoc.backend.service.FAQService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faq")
public class FAQRestController {

    private final FAQService faqService;

    @GetMapping("/")
    public List<FAQEntity> getCategories() { return faqService.getAll(); }
    
    public FAQRestController(FAQService faqService) {
        this.faqService = faqService;
    }

    @GetMapping("/{id}")
    public FAQEntity getFAQById(@PathVariable("id") Long id) {
        return faqService.getById(id);
    }

    @PostMapping()
    public FAQEntity postFAQ(@RequestBody FAQEntity faq) {
        return faqService.create(faq);
    }

    @PutMapping("/{id}")
    public FAQEntity putFAQById(@PathVariable("id") Long id, @RequestBody FAQEntity faq) {
        return faqService.updateById(id, faq);
    }
    @PutMapping("/order/")
    public List<FAQEntity> putFAQById(@RequestBody List<OrderModel> faq) {
        return faqService.updateAllOrder(faq);
    }

    @DeleteMapping("/{id}")
    public String deleteFAQById(@PathVariable("id") Long id) {
        return faqService.deleteById(id);
    }

}
