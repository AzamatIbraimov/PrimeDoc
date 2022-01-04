package one.primedoc.backend.controller;

import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.entity.PaymentEntity;
import one.primedoc.backend.service.PaymentService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {

    private  final PaymentService paymentService;

    public PaymentRestController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public List<PaymentEntity> getPayments() { return paymentService.getAll(); }

    @GetMapping("/{id}")
    public PaymentEntity getPaymentById(@PathVariable("id") Long id) {
        return paymentService.getById(id);
    }

    @PostMapping()
    public PaymentEntity postPayment(@ModelAttribute("payment") PaymentEntity payment, @Nullable @ModelAttribute("imageFile") MultipartFile imageFile) {
        return paymentService.create(payment, imageFile);
    }

    @PutMapping("/{id}")
    public PaymentEntity putPaymentById(@PathVariable("id") Long id, @RequestBody PaymentEntity payment) {
        return paymentService.updateById(id, payment);
    }

    @PutMapping("/image/{id}")
    public PaymentEntity putCategoryImageById(@PathVariable("id") Long id, @ModelAttribute("imageFile") MultipartFile imageFile) {
        return paymentService.updateLogoById(id, imageFile);
    }

    @DeleteMapping("/{id}")
    public String deletePaymentById(@PathVariable("id") Long id) {
        return paymentService.deleteById(id);
    }
}

