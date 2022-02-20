package one.primedoc.backend.repository;

import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.model.CategoryReservationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{
    @Query("SELECT new one.primedoc.backend.model.CategoryReservationModel(category.id, category.image, category.name, category.description) FROM category category")
    Page<CategoryReservationModel> getShortCategoryList(Pageable pageable);

    @Query("SELECT new one.primedoc.backend.model.CategoryReservationModel(category.id, category.image, category.name, category.description) FROM category category WHERE category.id = :id")
    CategoryReservationModel getShortCategoryListById(Long id);
}