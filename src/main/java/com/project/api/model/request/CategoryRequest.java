package com.project.api.model.request;

import com.project.api.model.entity.CategoryEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class CategoryRequest implements Serializable {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name must be at most 30 characters long")
    private String name;

    @Size(max = 100, message = "Description must be at most 100 characters long")
    private String description;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity toCategoryEntity() {
        CategoryEntity category = new CategoryEntity();
        category.setName(this.name);
        category.setDescription(this.description);
        return category;
    }
}
