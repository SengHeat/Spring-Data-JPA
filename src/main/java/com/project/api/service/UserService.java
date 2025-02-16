package com.project.api.service;

import com.project.api.model.entity.AddressEntity;
import com.project.api.model.entity.UserEntity;
import com.project.api.model.request.UserRequest;
import com.project.api.repository.AddressRepository;
import com.project.api.repository.UserRepository;
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

    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

}
