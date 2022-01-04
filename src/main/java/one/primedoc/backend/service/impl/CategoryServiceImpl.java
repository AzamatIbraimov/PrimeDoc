package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.CategoryEntity;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.exception.StorageException;
import one.primedoc.backend.model.CategoryFullModel;
import one.primedoc.backend.model.CategoryReservationModel;
import one.primedoc.backend.model.DoctorShortModel;
import one.primedoc.backend.repository.CategoryRepository;
import one.primedoc.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Value("${category.image.upload.path}")
    private String path;

    @Value("${image.url}")
    private String imageUrl;

    @Override
    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<CategoryReservationModel> getAllShort(Pageable pageable) {
        return categoryRepository.getShortCategoryList(pageable);
    }

    @Override
    public CategoryReservationModel getAllShortById(Long id) {
        return categoryRepository.getShortCategoryListById(id);
    }

    @Override
    public CategoryEntity getById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Category) Record not found with id: " + id));
    }

    @Override
    public CategoryEntity create(CategoryEntity category, MultipartFile imageFile) {
        if (imageFile != null) {
            if (imageFile.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            try {
                String fileName = imageFile.getOriginalFilename();
                InputStream is = imageFile.getInputStream();
                assert fileName != null;
                Files.copy(is, Paths.get(path + category.getName()
                                + "_" + fileName),
                        StandardCopyOption.REPLACE_EXISTING);
                category.setImage(imageUrl + category.getName()
                        + "_" + fileName);
                return categoryRepository.save(category);
            } catch (IOException e) {
                String msg = "Failed to store this file";
                throw new StorageException(msg, e);
            }
        } else {
            return categoryRepository.save(category);
        }
    }

    @Override
    public CategoryEntity updateById(Long id, CategoryEntity category) {
        return categoryRepository.findById(id)
                .map(newCategory -> {
                    newCategory.setName(category.getName());
                    newCategory.setDescription(category.getDescription());
                    newCategory.setIllness(category.getIllness());
                    return categoryRepository.save(newCategory);
                }).orElseThrow(() ->
                        new RecordNotFoundException("(Category) Record not found with id: " + id));
    }

    @Override
    public CategoryEntity updateImageById(Long id, MultipartFile imageFile) {
        if (imageFile.isEmpty()) {
            throw new StorageException("Failed to store empty file");
        }
        CategoryEntity category = categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFoundException("(Category) Record not found with id: " + id));
        try {
            String fileName = imageFile.getOriginalFilename();
            InputStream is = imageFile.getInputStream();
            assert fileName != null;
            Files.copy(is, Paths.get(path + category.getName()
                            + fileName.substring(fileName.lastIndexOf("."))),
                    StandardCopyOption.REPLACE_EXISTING);
            category.setImage(imageUrl + category.getName()
                    + fileName.substring(fileName.lastIndexOf(".")));
            return categoryRepository.save(category);
        } catch (IOException e) {

            String msg = "Failed to store this file";

            throw new StorageException(msg, e);
        }
    }

    @Override
    public String deleteById(Long id) {
        String imageFileURL = categoryRepository.getOne(id).getImage();
        if (imageFileURL != null) {
            String imageFileName = imageFileURL.substring(imageFileURL.lastIndexOf("/") + 1);
            Path filetoDeletePath = Paths.get(path + imageFileName);
            try {
                Files.delete(filetoDeletePath);
            } catch (IOException e) {
                String msg = "Failed to delete this file";

                throw new StorageException(msg, e);
            }
        }
        categoryRepository.deleteById(id);
        return "(Category) Record with id: " + id + " has been deleted!";
    }

    @Override
    public CategoryFullModel getAllFullById(Long id) {
        return categoryRepository.findById(id).map(category ->
                CategoryFullModel.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .illness(category.getIllness())
                        .doctors(category.getDoctors().stream().map(doctor ->
                                DoctorShortModel.builder()
                                        .id(doctor.getId())
                                        .firstName(doctor.getUser().getFirstName())
                                        .lastName(doctor.getUser().getLastName())
                                        .patronymic(doctor.getUser().getPatronymic())
                                        .position(doctor.getPosition())
                                        .image(doctor.getImage())
                                        .build()).collect(Collectors.toSet()))
                        .image(category.getImage()).build()
        ).orElseThrow(() ->
                new RecordNotFoundException("(Category) Record not found with id: " + id));
    }
}
