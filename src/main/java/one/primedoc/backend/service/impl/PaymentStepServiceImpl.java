package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.PaymentStepEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.repository.PaymentStepRepository;
import one.primedoc.backend.service.PaymentStepService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentStepServiceImpl implements PaymentStepService {
    private final PaymentStepRepository paymentStepRepository;

    public PaymentStepServiceImpl(PaymentStepRepository paymentStepRepository) { this.paymentStepRepository = paymentStepRepository; }

    @Override
    public List<PaymentStepEntity> getAll() {
        return paymentStepRepository.findAll();
    }

    @Override
    public PaymentStepEntity getById(Long id) {
        return paymentStepRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(PaymentStep) Record not found with id: " + id));
    }

    @Override
    public PaymentStepEntity create(PaymentStepEntity paymentStep) {
        return paymentStepRepository.save(paymentStep);
    }

    @Override
    public PaymentStepEntity updateById(Long id, PaymentStepEntity paymentStep) {
        return paymentStepRepository.findById(id)
                .map(newPaymentStep -> {
                    newPaymentStep.setNumber(paymentStep.getNumber());
                    newPaymentStep.setText(paymentStep.getText());
                    return paymentStepRepository.save(newPaymentStep);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(PaymentStep) Record not found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {
        paymentStepRepository.deleteById(id);
        return "(PaymentStep) Record with id: " + id + " has been deleted!";
    }
}