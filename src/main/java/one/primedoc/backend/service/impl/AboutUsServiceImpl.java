package one.primedoc.backend.service.impl;


import one.primedoc.backend.entity.statics.AboutUsEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.model.OrderModel;
import one.primedoc.backend.repository.AboutUsRepository;
import one.primedoc.backend.service.AboutUsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AboutUsServiceImpl implements AboutUsService {
    private final AboutUsRepository aboutUsRepository;

    public AboutUsServiceImpl(AboutUsRepository aboutUsRepository) {
        this.aboutUsRepository = aboutUsRepository;
    }

    @Override
    public List<AboutUsEntity> getAll() {
        return aboutUsRepository.findAll();
    }

    @Override
    public AboutUsEntity getById(Long id) {
        return aboutUsRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(AboutUs) Record not found with id: " + id));
    }

    @Override
    public AboutUsEntity create(AboutUsEntity aboutUs) {
        return aboutUsRepository.save(aboutUs);
    }

    @Override
    public AboutUsEntity updateById(Long id, AboutUsEntity aboutUs) {
        return aboutUsRepository.findById(id)
                .map(newAboutUs -> {
                    newAboutUs.setHeader(aboutUs.getHeader());
                    newAboutUs.setParagraph(aboutUs.getParagraph());
//                    newAboutUs.setImage(aboutUs.getImage());
                    newAboutUs.setOrder(aboutUs.getOrder());
                    return aboutUsRepository.save(newAboutUs);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(AboutUs) Record not found with id: " + id));
    }

    @Override
    public List<AboutUsEntity> updateAllOrder(List<OrderModel> aboutUs) {
        List<AboutUsEntity> old = aboutUsRepository.findAll();
        for (int i = 0; i < aboutUs.size(); i++) {
            old.get(i).setOrder(aboutUs.get(i).getOrder());
        }
        return aboutUsRepository.saveAll(old);
    }

    @Override
    public String deleteById(Long id) {
        aboutUsRepository.deleteById(id);
        return "(AboutUs) Record with id: " + id + " has been deleted!";
    }
}
