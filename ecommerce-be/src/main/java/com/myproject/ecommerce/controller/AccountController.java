package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.dto.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<AccountResponse>> createUserAccount(@RequestBody @Valid AccountRequest accountRequest){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.creataUserAccount(accountRequest)
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(apiResponse);
    }

    @GetMapping("/api/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getListAccount(){

        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.getListAccount()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/api/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getUserAccount(@PathVariable Long id){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.getUserAccount(id)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/api/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>>  updateUserAccount(@PathVariable Long id,
                                                             @RequestBody AccountRequest accountRequest){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.updateAccount(id, accountRequest)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/api/accounts/{id}")
    public ResponseEntity<Void> deleteUserAccount(@PathVariable Long id){
        accountService.deleteUserAccount(id);
        return ResponseEntity.noContent().build();
    }
}
