package one.primedoc.backend.controller;

import one.primedoc.backend.entity.PaymentStepEntity;
import one.primedoc.backend.service.PaymentStepService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment/step")
public class PaymentStepRestController {

    private final PaymentStepService paymentStepService;

    public PaymentStepRestController(PaymentStepService paymentStepService) {
        this.paymentStepService = paymentStepService;
    }

    @GetMapping("/")
    public List<PaymentStepEntity> getPaymentSteps() { return paymentStepService.getAll(); }

    @GetMapping("/{id}")
    public PaymentStepEntity getPaymentStepById(@PathVariable("id") Long id) {
        return paymentStepService.getById(id);
    }

    @PostMapping()
    public PaymentStepEntity postPaymentStep(@RequestBody PaymentStepEntity paymentStep) { return paymentStepService.create(paymentStep); }

    @PutMapping("/{id}")
    public PaymentStepEntity putPaymentStepById(@PathVariable("id") Long id, @RequestBody PaymentStepEntity paymentStep) {
        return paymentStepService.updateById(id, paymentStep);
    }

    @DeleteMapping("/{id}")
    public String deletePaymentStepById(@PathVariable("id") Long id) {
        return paymentStepService.deleteById(id);
    }
}

