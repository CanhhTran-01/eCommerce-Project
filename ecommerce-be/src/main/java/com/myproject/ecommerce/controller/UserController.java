package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.CustomerResponse;
import com.myproject.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.createCustomer(userRequest));
    }

    @GetMapping("/api/customers")
    public ResponseEntity<List<CustomerResponse>> getCustomerList(){
        return ResponseEntity.ok(userService.getCustomerList());
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                           @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updateCustomer(id, userRequest));
    }

    @DeleteMapping("/api/customers/{id}")
    public String deleteCustomer(@PathVariable Long id){
        userService.deleteCustomer(id);
        return ("Deleted Customer with id " + id + " ! ");
    }
}
