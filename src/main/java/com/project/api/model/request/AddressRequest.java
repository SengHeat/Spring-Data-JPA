package com.project.api.model.request;

import com.project.api.model.entity.AddressEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class AddressRequest implements Serializable {

    @NotBlank(message = "Address is required")
    @Size(max = 50, message = "Address must be at most 30 characters long")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressEntity toAddressEntity() {
        AddressEntity address = new AddressEntity();
        address.setAddress(this.address);
        return address;
    }

    @Override
    public String toString() {
        return "AddressRequest{" +
                "address='" + address + '\'' +
                '}';
    }
}
