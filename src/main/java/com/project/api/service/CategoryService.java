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
            CategoryEntity findData = categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Category with ID " + id + " not found!"));

            // Check if the name already exists
            if (categoryRepository.existsByName(request.getName())) {
                String errorMessage = "Category name '" + request.getName() + "' already exists!";
                throw new AlreadyExistsException(errorMessage);
            }

            // Update the category data
            findData.setName(request.getName());
            findData.setDescription(request.getDescription());
        try {
            // Save and return updated entity
            return this.categoryRepository.save(findData);

        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<CategoryEntity> getAll() {
        return this.categoryRepository.findAll();
    }

}
