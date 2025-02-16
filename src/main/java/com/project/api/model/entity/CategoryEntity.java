package com.project.api.model.entity;


import jakarta.persistence.*;

import java.util.Objects;
import java.util.logging.Logger;

@Entity(name = "Category")
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    private static final Logger log = Logger.getLogger(CategoryEntity.class.getName());



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CategoryEntity category = (CategoryEntity) o;
        return Objects.equals(id, category.id) && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @PrePersist
    public void beforeSave() {
        log.info("A new category is about to be saved: " + this.toString());
    }

    @PostPersist
    public void afterSave() {
        log.info("Category saved successfully: " + this.toString());
    }

    @PreUpdate
    public void beforeUpdate() {
        log.info("Category is about to be updated: " + this.toString());
    }

    @PostUpdate
    public void afterUpdate() {
        log.info("Category updated successfully: " + this.toString());
    }

    @PreRemove
    public void beforeDelete() {
        log.warning("Category is about to be deleted: " + this.toString());
    }

    @PostRemove
    public void afterDelete() {
        log.warning("Category deleted successfully: " + this.toString());
    }

    @PostLoad
    public void afterLoad() {
        log.info("Category entity loaded: " + this.toString());
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
