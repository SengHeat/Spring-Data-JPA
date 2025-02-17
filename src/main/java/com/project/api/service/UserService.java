package com.project.api.service;

import com.project.api.exception.NotFoundException;
import com.project.api.model.entity.AddressEntity;
import com.project.api.model.entity.UserEntity;
import com.project.api.model.request.UserRequest;
import com.project.api.repository.AddressRepository;
import com.project.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public UserEntity create(UserRequest request) throws Exception {
        // Create a new UserEntity
        UserEntity user = new UserEntity();
        user.setName(request.getName());

        // Save the user first, without address if address is null
        user = userRepository.save(user);

        // Check if the address is provided in the request
        if (request.getAddress() != null) {
            // Create AddressEntity only if address is provided
            AddressEntity address = new AddressEntity();
            address.setAddress(request.getAddress().getAddress());

            // Set the user reference in the address entity
            address.setUser(user);

            // Save the address and link it to the user
            address = addressRepository.save(address);

            // Set the saved address to the user
            user.setAddress(address);
        }

        // Return the saved user entity (with or without address)
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("Error saving user: " + e.getMessage());
        }
    }

    public UserEntity findOne(String idStr) {
        try{
            Long id = Long.parseLong(idStr);
            return this.userRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("No user found with the provided ID: " + id)
            );
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }
    }

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

    public UserEntity update(String idStr, UserRequest request) throws Exception {
        //find user from database before update
        UserEntity user = findOne(idStr);
        //update user from data request
        user.setName(request.getName());
        try {
            //return data after update to frontend
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @Transactional
    public UserEntity delete(String idStr) throws Exception {
        UserEntity user = findOne(idStr);
        try {
            // Ensure the user has no dependent records (foreign key constraint issue)
            // Delete related entities (e.g., addresses) first if cascade delete is not set
            if(user.getAddress() != null) {
                AddressEntity address = user.getAddress();
            }
            // Delete the user from the repository
            this.userRepository.deleteById(user.getId());
            return user;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
