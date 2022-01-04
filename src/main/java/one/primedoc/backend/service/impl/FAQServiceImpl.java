package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.statics.FAQEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.OrderModel;
import one.primedoc.backend.repository.FAQRepository;
import one.primedoc.backend.service.FAQService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FAQServiceImpl implements FAQService {
    private final FAQRepository faqRepository;

    public FAQServiceImpl(FAQRepository faqRepository) {
        this.faqRepository = faqRepository;
    }

    @Override
    public List<FAQEntity> getAll() {
        return faqRepository.findAll();
    }

    @Override
    public FAQEntity getById(Long id) {
        return faqRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(FAQ) Record not found with id: " + id));
    }

    @Override
    public FAQEntity create(FAQEntity faq) {
        return faqRepository.save(faq);
    }

    @Override
    public FAQEntity updateById(Long id, FAQEntity faq) {
        return faqRepository.findById(id)
                .map(newFAQ -> {
                    newFAQ.setOrder(faq.getOrder());
                    newFAQ.setAnswer(faq.getAnswer());
                    newFAQ.setQuestion(faq.getQuestion());
                    return faqRepository.save(newFAQ);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(FAQ) Record not found with id: " + id));
    }

    @Override
    public List<FAQEntity> updateAllOrder(List<OrderModel> faq) {
        List<FAQEntity> old = faqRepository.findAll();
        for (int i = 0; i < faq.size(); i++) {
            old.get(i).setOrder(faq.get(i).getOrder());
        }
        return faqRepository.saveAll(old);
    }

    @Override
    public String deleteById(Long id) {
        faqRepository.deleteById(id);
        return "(FAQ) Record with id: " + id + " has been deleted!";
    }
}
