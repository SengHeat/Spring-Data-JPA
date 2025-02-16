package com.project.api.controller;


import com.project.api.exception.AlreadyExistsException;
import com.project.api.model.base.ApiResponse;
import com.project.api.model.entity.CategoryEntity;
import com.project.api.model.request.CategoryRequest;
import com.project.api.model.response.CategoryResponse;
import com.project.api.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse> update(@PathVariable("id") String idStr, @RequestBody CategoryRequest request) throws Exception {
        try {
            Long id = Long.parseLong(idStr); // Convert String to Long
            CategoryEntity updatedCategory = categoryService.update(id, request);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", 200, updatedCategory));
        } catch (NumberFormatException e) {
            throw  new NumberFormatException(e.getMessage());
        } catch (AlreadyExistsException e) {
            throw new AlreadyExistsException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    @GetMapping()
    public ResponseEntity<ApiResponse> findAll() {
        List<CategoryResponse> responseList = categoryService.findAll()
                .stream()
                .map(CategoryResponse::fromCategoryEntity)
                .toList();

        return ResponseEntity.ok(new ApiResponse("", 200, responseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable("id") String idStr) {
        try {
            Long id = Long.parseLong(idStr);
            CategoryEntity category = this.categoryService.findOne(id);
            return ResponseEntity.ok(new ApiResponse("Successfully", 200, CategoryResponse.fromCategoryEntity(category)));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable("id") String idStr){
        try{
            Long id = Long.parseLong(idStr);
            CategoryEntity category = this.categoryService.delete(id);
            return ResponseEntity.ok(new ApiResponse("category delete successfully!", 200, null));
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

}
