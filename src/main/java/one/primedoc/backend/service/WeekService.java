package one.primedoc.backend.service;

import one.primedoc.backend.entity.WeekEntity;

import java.util.List;

public interface WeekService {
    public List<WeekEntity> getAll();
    public WeekEntity getById(Long id);
    public WeekEntity create(WeekEntity week);
    public WeekEntity updateById(Long id, WeekEntity week);
    public String deleteById(Long id);
}
