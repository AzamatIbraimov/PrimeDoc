package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.WeekEntity;
import one.primedoc.backend.entity.WeekEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.repository.WeekRepository;
import one.primedoc.backend.service.WeekService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeekServiceImpl implements WeekService {
    private final WeekRepository weekRepository;

    public WeekServiceImpl(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
    }


    @Override
    public List<WeekEntity> getAll() {
        return weekRepository.findAll();
    }

    @Override
    public WeekEntity getById(Long id) {
        return weekRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Week) Record not found with id: " + id));
    }

    @Override
    public WeekEntity create(WeekEntity week) {
        return weekRepository.save(week);
    }

    @Override
    public WeekEntity updateById(Long id, WeekEntity week) {
        return weekRepository.findById(id)
                .map(newWeek -> {
                    newWeek.setWeekOrder(week.getWeekOrder());
                    return weekRepository.save(newWeek);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Week) Record not found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {
        weekRepository.deleteById(id);
        return "(Week) Record with id: " + id + " has been deleted!";
    }

}
