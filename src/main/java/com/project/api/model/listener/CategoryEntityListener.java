package com.project.api.model.listener;

import com.project.api.model.entity.CategoryEntity;
import jakarta.persistence.*;
import java.util.logging.Logger;

public class CategoryEntityListener {

    private static final Logger log = Logger.getLogger(CategoryEntityListener.class.getName());

    @PrePersist
    public void beforeSave(CategoryEntity category) {
        log.info("A new category is about to be saved: " + category);
    }

    @PostPersist
    public void afterSave(CategoryEntity category) {
        log.info("Category saved successfully: " + category);
    }

    @PreUpdate
    public void beforeUpdate(CategoryEntity category) {
        log.info("Category is about to be updated: " + category);
    }

    @PostUpdate
    public void afterUpdate(CategoryEntity category) {
        log.info("Category updated successfully: " + category);
    }

    @PreRemove
    public void beforeDelete(CategoryEntity category) {
        log.warning("Category is about to be deleted: " + category);
    }

    @PostRemove
    public void afterDelete(CategoryEntity category) {
        log.warning("Category deleted successfully: " + category);
    }

    @PostLoad
    public void afterLoad(CategoryEntity category) {
        log.info("Category entity loaded: " + category);
    }
}
