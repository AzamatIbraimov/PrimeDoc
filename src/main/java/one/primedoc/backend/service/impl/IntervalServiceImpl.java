package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.IntervalEntity;
import one.primedoc.backend.entity.IntervalEntity;
import one.primedoc.backend.exception.InvalidValueException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.repository.IntervalRepository;
import one.primedoc.backend.service.IntervalService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class IntervalServiceImpl implements IntervalService {
    private final IntervalRepository intervalRepository;

    public IntervalServiceImpl(IntervalRepository intervalRepository) {
        this.intervalRepository = intervalRepository;
    }


    @Override
    public List<IntervalEntity> getAll() {
        return intervalRepository.findAll();
    }

    @Override
    public IntervalEntity getById(Long id) {
        return intervalRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Interval) Record not found with id: " + id));
    }

    @Override
    public IntervalEntity create(IntervalEntity interval) {
        Long start = interval.getStart().getTime();
        Long end = interval.getEnd().getTime();
        if (start > end) {
            intervalRepository.save(interval);
            System.out.println("SPECIAL INTERVAL " + interval);
        } else if ((end - start) <= TimeUnit.MINUTES.toMillis(30))
            throw new InvalidValueException("(Interval) Invalid value for start and end time. Interval less than 30 minutes.");
        return intervalRepository.save(interval);
    }

    @Override
    public IntervalEntity updateById(Long id, IntervalEntity interval) {
        return intervalRepository.findById(id)
                .map(newInterval -> {
                    newInterval.setStart(interval.getStart());
                    newInterval.setEnd(interval.getEnd());
                    return intervalRepository.save(newInterval);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Interval) Record not found with id: " + id));
    }

    @Override
    public String deleteById(Long id) {
        intervalRepository.deleteById(id);
        return "(Interval) Record with id: " + id + " has been deleted!";
    }

}
