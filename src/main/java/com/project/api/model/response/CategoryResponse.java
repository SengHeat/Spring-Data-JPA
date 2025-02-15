package com.project.api.model.response;

import com.project.api.model.entity.CategoryEntity;

import java.io.Serializable;

public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static CategoryResponse fromCategoryEntity(CategoryEntity categoryEntity) {
        return new CategoryResponse(
                categoryEntity.getId(),
                categoryEntity.getName(),
                categoryEntity.getDescription()
        );
    }
}

