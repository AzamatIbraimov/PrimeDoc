package one.primedoc.backend.service;


import one.primedoc.backend.entity.statics.AboutUsEntity;
import one.primedoc.backend.model.OrderModel;

import java.util.List;

public interface AboutUsService {

    public List<AboutUsEntity> getAll();

    public AboutUsEntity getById(Long id);

    public AboutUsEntity create(AboutUsEntity aboutUs);

    public AboutUsEntity updateById(Long id, AboutUsEntity aboutUs);

    public List<AboutUsEntity> updateAllOrder(List<OrderModel> aboutUs);

    public String deleteById(Long id);

}
