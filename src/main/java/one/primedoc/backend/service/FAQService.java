package one.primedoc.backend.service;

import one.primedoc.backend.entity.statics.FAQEntity;
import one.primedoc.backend.model.OrderModel;

import java.util.List;

public interface FAQService {
    public List<FAQEntity> getAll();
    public FAQEntity getById(Long id);
    public FAQEntity create(FAQEntity faq);
    public FAQEntity updateById(Long id, FAQEntity faq);
    public String deleteById(Long id);
    public List<FAQEntity> updateAllOrder(List<OrderModel> faq);
}

