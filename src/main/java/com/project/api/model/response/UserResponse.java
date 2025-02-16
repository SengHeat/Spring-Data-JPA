package com.project.api.model.response;

import com.project.api.model.entity.AddressEntity;
import com.project.api.model.entity.UserEntity;
import java.io.Serializable;

public class UserResponse implements Serializable {

    private Long id;
    private String name;
    private AddressResponse address; // ❌ Removed static

    public UserResponse(Long id, String name, AddressResponse address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

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

    public AddressResponse getAddress() {
        return address;
    }

    public void setAddress(AddressResponse address) {
        this.address = address;
    }

    public static UserResponse fromUserEntity(UserEntity userEntity) {
        // Ensure address is not null before creating AddressResponse
        AddressResponse addressResponse = null;
        if (userEntity.getAddress() != null) {
            addressResponse = new AddressResponse(
                    userEntity.getAddress().getId(),
                    userEntity.getAddress().getAddress(),
                    userEntity.getAddress().getUser().getId()
            );
        }

        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                addressResponse // ✅ This ensures `null` safety
        );
    }
}
