package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.service.AccountService;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/api/accounts")
    public ResponseEntity<ApiResponse<AccountResponse>> createAccount(@RequestBody @Valid AccountRequest accountRequest){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.createAccount(accountRequest)
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
    public ResponseEntity<ApiResponse<AccountResponse>> getAccount(@PathVariable Long id){

        // take auth from current account
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {}", authentication.getName());
        authentication.getAuthorities().forEach(auth -> log.info(auth.getAuthority()));

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.getAccount(id)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/api/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>>  updateAccount(@PathVariable Long id,
                                                             @RequestBody AccountRequest accountRequest){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.updateAccount(id, accountRequest)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/api/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
