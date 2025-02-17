package com.project.api.controller;

import com.project.api.model.base.ApiResponse;
import com.project.api.model.entity.AddressEntity;
import com.project.api.model.request.AddressRequest;
import com.project.api.model.response.AddressResponse;
import com.project.api.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("create/{id}")
    public ResponseEntity<ApiResponse> create(@PathVariable("id") String idStr,@Valid @RequestBody AddressRequest request) {
        AddressEntity address = this.addressService.create(request, idStr);
        return ResponseEntity.ok(new ApiResponse("Address created successfully", 200, AddressResponse.fromAddressEntity(address)));
    }
}
