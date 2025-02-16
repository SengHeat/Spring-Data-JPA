package com.project.api.service;

import com.project.api.exception.AlreadyExistsException;
import com.project.api.exception.NotFoundException;
import com.project.api.model.entity.CategoryEntity;
import com.project.api.model.request.CategoryRequest;
import com.project.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public CategoryEntity create(CategoryRequest request) throws Exception {

        //prepare request to entity
        CategoryEntity data = request.toCategoryEntity();

        //check name from request exit or not
        if(categoryRepository.existsByName(data.getName())){
            throw new AlreadyExistsException("Category name already exists!");
        }

        try{
            return this.categoryRepository.save(request.toCategoryEntity());
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public CategoryEntity update(Long id, CategoryRequest request) throws Exception {
        // Find the category
        CategoryEntity category = findOne(id);
        // Check if the name already exists
        if (categoryRepository.existsByName(request.getName())) {
            throw new AlreadyExistsException("Category name already exists!");
        }

        // Update the category data
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return this.categoryRepository.save(category);

    }

    public List<CategoryEntity> findAll() {
        return this.categoryRepository.findAll();
    }

    public CategoryEntity findOne(Long id) throws NotFoundException {
        return this.categoryRepository.findById(id).orElseThrow(() -> new
                NotFoundException("Category with ID " + id + " not found!"));
    }

    public CategoryEntity delete(Long id) {
        CategoryEntity category = findOne(id);
        this.categoryRepository.deleteById(id);
        return category;
    }

}
