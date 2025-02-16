package com.project.api.controller;


import com.project.api.exception.InvalidIdFormatException;
import com.project.api.model.base.ApiResponse;
import com.project.api.model.entity.CategoryEntity;
import com.project.api.model.request.CategoryRequest;
import com.project.api.model.response.CategoryResponse;
import com.project.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CategoryRequest request) throws Exception {
        CategoryEntity categoryEntity = categoryService.create(request);
        CategoryResponse response = CategoryResponse.fromCategoryEntity(categoryEntity);
        return ResponseEntity.ok(new ApiResponse("", 200, response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("id") String idStr, @RequestBody CategoryRequest request) {
        try {
            Long id = Long.parseLong(idStr); // Convert String to Long
            CategoryEntity updatedCategory = categoryService.update(id, request);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", 200, updatedCategory));
        } catch (NumberFormatException e) {
            throw new InvalidIdFormatException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping()
    public ResponseEntity<ApiResponse> getAll() {
        List<CategoryResponse> responseList = categoryService.getAll()
                .stream()
                .map(CategoryResponse::fromCategoryEntity)
                .toList();

        return ResponseEntity.ok(new ApiResponse("", 200, responseList));
    }

}
