package com.project.api.model.request;

import com.project.api.model.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class UserRequest implements Serializable {

    @NotBlank(message = "Name is required")
    @Size(max = 30, message = "Name must be at most 30 characters long")
    private String name;

    private AddressRequest address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressRequest getAddress() {
        return address;
    }

    public void setAddress(AddressRequest address) {
        this.address = address;
    }

    public UserEntity toUserEntity() {
        UserEntity user = new UserEntity();
        user.setName(this.name);
        return user;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", address=" + address.toString() +
                '}';
    }
}
