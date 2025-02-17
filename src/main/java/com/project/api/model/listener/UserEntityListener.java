package com.project.api.model.listener;

import com.project.api.model.entity.UserEntity;
import jakarta.persistence.*;

import java.util.logging.Logger;

public class UserEntityListener {

    // Called before the entity is persisted (insert operation)
    private static final Logger log = Logger.getLogger(UserEntityListener.class.getName());

    @PrePersist
    public void prePersist(UserEntity userEntity) {
        log.info("PrePersist: Preparing to persist user: " + userEntity);
        if (userEntity.getName() == null || userEntity.getName().isEmpty()) {
            userEntity.setName("Default Name");
        }
    }

    // Called after the entity is persisted (insert operation)
    @PostPersist
    public void postPersist(UserEntity userEntity) {
        log.info("PostPersist: User has been saved: " + userEntity);
        // Post-save logic like logging or triggering related actions can be added here
    }

    // Called before the entity is updated (update operation)
    @PreUpdate
    public void preUpdate(UserEntity userEntity) {
        log.info("PreUpdate: Preparing to update user: " + userEntity);
        // You could add logic to handle changes before the entity is updated, like validating data
    }

    // Called after the entity is updated (update operation)
    @PostUpdate
    public void postUpdate(UserEntity userEntity) {
        log.info("PostUpdate: User has been updated: " + userEntity);
        // Additional logic to handle after an update (e.g., updating related entities)
    }

    // Called before the entity is removed (delete operation)
    @PreRemove
    public void preRemove(UserEntity userEntity) {
        log.info("PreRemove: Preparing to delete user: " + userEntity);
        // You could perform some cleanup before deletion, like removing related data
    }

    // Called after the entity is removed (delete operation)
    @PostRemove
    public void postRemove(UserEntity userEntity) {
        log.info("PostRemove: User has been deleted: " + userEntity);
        // Additional cleanup actions after removal (e.g., cleaning up related data)
    }

    // Called after the entity is loaded from the database (select operation)
    @PostLoad
    public void postLoad(UserEntity userEntity) {
        log.info("PostLoad: User has been loaded from the database: " + userEntity);
        // Initialization or logging after the entity is loaded from the database
    }
}
