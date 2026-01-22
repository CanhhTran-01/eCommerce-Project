package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.UserResponse;
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
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @GetMapping("/api/customers")
    public ResponseEntity<List<UserResponse>> getUserList(){
        return ResponseEntity.ok(userService.getUserList());
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id,
                                                       @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/api/customers/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ("Deleted Customer with id " + id + " ! ");
    }
}
