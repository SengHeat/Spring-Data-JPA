package com.project.api.model.response;

import com.project.api.model.entity.AddressEntity;

public class AddressResponse {

    private Long id;
    private String address;

    private Long userId;


    public AddressResponse(Long id, String address, Long userId) {
        this.id = id;
        this.address = address;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static AddressResponse fromAddressEntity(AddressEntity entity) {
        return new AddressResponse(
                entity.getId(),
                entity.getAddress(),
                entity.getUser().getId()
        );
    }
}
