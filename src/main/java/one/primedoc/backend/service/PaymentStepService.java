package one.primedoc.backend.service;

import one.primedoc.backend.entity.PaymentStepEntity;

import java.util.List;

public interface PaymentStepService {
    public List<PaymentStepEntity> getAll();
    public PaymentStepEntity getById(Long id);
    public PaymentStepEntity create(PaymentStepEntity payment);
    public PaymentStepEntity updateById(Long id, PaymentStepEntity payment);
    public String deleteById(Long id);
}
