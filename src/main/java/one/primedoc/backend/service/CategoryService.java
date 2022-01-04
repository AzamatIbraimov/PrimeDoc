package one.primedoc.backend.service;

import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.model.CategoryFullModel;
import one.primedoc.backend.model.CategoryReservationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {
    public List<CategoryEntity> getAll();
    public Page<CategoryReservationModel> getAllShort(Pageable pageable);
    public CategoryReservationModel getAllShortById(Long id);
    public CategoryEntity getById(Long id);
    public CategoryEntity create(CategoryEntity category, MultipartFile imageFile);
    public CategoryEntity updateById(Long id, CategoryEntity category);
    public CategoryEntity updateImageById(Long id, MultipartFile imageFile);
    public String deleteById(Long id);
    public CategoryFullModel getAllFullById(Long id);
}
