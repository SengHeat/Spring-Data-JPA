package com.project.api.controller;


import com.project.api.model.entity.CategoryEntity;
import com.project.api.model.request.CategoryRequest;
import com.project.api.model.response.CategoryResponse;
import com.project.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) throws Exception {
        CategoryEntity categoryEntity = categoryService.create(request);
        CategoryResponse response = CategoryResponse.fromCategoryEntity(categoryEntity);
        return ResponseEntity.ok(response);
    }

}
