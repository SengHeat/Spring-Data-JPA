package com.project.api.model.listener;

import com.project.api.model.entity.AddressEntity;
import com.project.api.model.entity.UserEntity;
import jakarta.persistence.*;

import java.util.logging.Logger;

public class AddressEntityListener {

    private static final Logger log = Logger.getLogger(AddressEntityListener.class.getName());

    // This method is called before the AddressEntity is persisted to the database
    @PrePersist
    public void prePersist(AddressEntity address) {
        log.info("AddressEntity is about to be persisted: " + address.getAddress());

        // Validation example: Check if Address has a valid User entity.
        if (address.getUser() == null) {
            throw new IllegalArgumentException("Address must be associated with a User.");
        }
    }

    // This method is called after the AddressEntity is persisted to the database
    @PostPersist
    public void postPersist(AddressEntity address) {
        log.info("AddressEntity has been persisted: " + address.getAddress());
        
        // Example: After saving the Address, you might log it or update other entities.
    }

    // This method is called before the AddressEntity is updated in the database
    @PreUpdate
    public void preUpdate(AddressEntity address) {
        log.info("AddressEntity is about to be updated: " + address.getAddress());

        // Example: You could validate if the user is still valid or perform additional checks.
        if (address.getUser() == null) {
            throw new IllegalArgumentException("Address must be associated with a User.");
        }
    }

    // This method is called after the AddressEntity is updated in the database
    @PostUpdate
    public void postUpdate(AddressEntity address) {
        log.info("AddressEntity has been updated: " + address.getAddress());

        // Example: Perform any actions after the update, like caching or sending a notification.
    }

    // This method is called before the AddressEntity is removed from the database
    @PreRemove
    public void preRemove(AddressEntity address) {
        log.info("AddressEntity is about to be removed: " + address.getAddress());

        // Example: You could clean up related data or check if removing is allowed.
    }

    // This method is called after the AddressEntity is removed from the database
    @PostRemove
    public void postRemove(AddressEntity address) {
        log.info("AddressEntity has been removed: " + address.getAddress());

        // Example: Perform any post-remove actions like logging or updating related entities.
    }

    // This method is called after the AddressEntity is loaded from the database
    @PostLoad
    public void postLoad(AddressEntity address) {
        log.info("AddressEntity has been loaded: " + address.getAddress());

        // Example: Perform any actions after loading, such as initialization or logging.
    }
}
