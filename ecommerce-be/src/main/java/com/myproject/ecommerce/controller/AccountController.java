package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/api/accounts")
    public ResponseEntity<AccountResponse> createUserAccount(@RequestBody @Valid AccountRequest accountRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.creataUserAccount(accountRequest));
    }

    @GetMapping("/api/accounts")
    public ResponseEntity<List<AccountResponse>> getListAccount(){
        return ResponseEntity.ok(accountService.getListAccount());
    }

    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<AccountResponse> getUserAccount(@PathVariable Long id){
        return ResponseEntity.ok(accountService.getUserAccount(id));
    }

    @PutMapping("/api/accounts/{id}")
    public ResponseEntity<AccountResponse> updateUserAccount(@PathVariable Long id,
                                                             @RequestBody AccountRequest accountRequest){
        return ResponseEntity.ok(accountService.updateAccount(id, accountRequest));
    }

    @DeleteMapping("/api/accounts/{id}")
    public String deleteUserAccount(@PathVariable Long id){
        accountService.deleteUserAccount(id);
        return ("Deleted User Account with id " + id + " ! ");
    }
}
