package one.primedoc.backend.service;

import one.primedoc.backend.entity.PaymentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PaymentService {
    public List<PaymentEntity> getAll();
    public PaymentEntity getById(Long id);
    public PaymentEntity create(PaymentEntity payment, MultipartFile imageFile);
    public PaymentEntity updateById(Long id, PaymentEntity payment);
    public PaymentEntity updateLogoById(Long id, MultipartFile imageFile);
    public String deleteById(Long id);
}
