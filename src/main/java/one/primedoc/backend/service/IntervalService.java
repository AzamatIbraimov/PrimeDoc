package one.primedoc.backend.service;

import one.primedoc.backend.entity.IntervalEntity;

import java.util.List;

public interface IntervalService {
    public List<IntervalEntity> getAll();
    public IntervalEntity getById(Long id);
    public IntervalEntity create(IntervalEntity interval);
    public IntervalEntity updateById(Long id, IntervalEntity interval);
    public String deleteById(Long id);
}
