package one.primedoc.backend.controller;


import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.model.CategoryFullModel;
import one.primedoc.backend.model.CategoryReservationModel;
import one.primedoc.backend.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryRestController {

    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<CategoryEntity> getCategories() {
        return categoryService.getAll();
    }

    @GetMapping("/info")
    public Page<CategoryReservationModel> getShortCategories(@RequestParam("page") Integer page, @RequestParam(value = "size", defaultValue = "7") Integer size) {
        return categoryService.getAllShort(PageRequest.of(page, size));
    }

    @GetMapping("/info/{id}")
    public CategoryReservationModel getShortCategoriesById(@PathVariable("id") Long id) {
        return categoryService.getAllShortById(id);
    }

    @GetMapping("/info/details/{id}")
    public CategoryFullModel getFullCategoriesById(@PathVariable("id") Long id) {
        return categoryService.getAllFullById(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public CategoryEntity getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    @PostMapping()
    public CategoryEntity postCategory(@ModelAttribute("category") CategoryEntity category, @Nullable @ModelAttribute("imageFile") MultipartFile imageFile) {
        return categoryService.create(category, imageFile);
    }

    @PutMapping("/{id}")
    public CategoryEntity putCategoryById(@PathVariable("id") Long id, @RequestBody CategoryEntity category) {
        return categoryService.updateById(id, category);
    }

    @PutMapping("/image/{id}")
    public CategoryEntity putCategoryImageById(@PathVariable("id") Long id, @ModelAttribute("imageFile") MultipartFile imageFile) {
        return categoryService.updateImageById(id, imageFile);
    }

    @DeleteMapping("/{id}")
    public String deleteCategoryById(@PathVariable("id") Long id) {
        return categoryService.deleteById(id);
    }
}
