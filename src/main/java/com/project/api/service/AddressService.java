package com.project.api.service;

import com.project.api.model.entity.AddressEntity;
import com.project.api.model.entity.UserEntity;
import com.project.api.model.request.AddressRequest;
import com.project.api.repository.AddressRepository;
import com.project.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    private final UserService userService;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(UserService userService, AddressRepository addressRepository) {
        this.userService = userService;
        this.addressRepository = addressRepository;
    }

    public AddressEntity create(AddressRequest request, String idStr) {
        // 1. Create a new AddressEntity object
        AddressEntity address = new AddressEntity();

        // 2. Map the data from the request to the AddressEntity fields
        address.setAddress(request.getAddress());
        // If user is provided in the request, set it
        UserEntity user = this.userService.findOne(idStr);
        address.setUser(user);
        try {
            // 3. Save the AddressEntity in the database
            return addressRepository.save(address);

        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create address: " + e.getMessage());
        }
    }

}
