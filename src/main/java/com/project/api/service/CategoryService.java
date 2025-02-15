package com.project.api.service;

import com.project.api.exception.CategoryNameAlreadyExistsException;
import com.project.api.model.entity.CategoryEntity;
import com.project.api.model.request.CategoryRequest;
import com.project.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public CategoryEntity create(CategoryRequest request) {

        //prepare request to entity
        CategoryEntity data = request.toCategoryEntity();

        //check name from request exit or not
        if(categoryRepository.existsByName(data.getName())){
            throw new CategoryNameAlreadyExistsException("Category name already exists!");
        }

        return this.categoryRepository.save(request.toCategoryEntity());
    }
}
