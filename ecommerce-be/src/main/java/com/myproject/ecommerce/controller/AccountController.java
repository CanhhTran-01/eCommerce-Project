package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.UserInfoResponse;
import com.myproject.ecommerce.service.AccountService;
import com.myproject.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @PostMapping("/accounts")
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

    @GetMapping("/me/info")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getInfo(@AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT

        ApiResponse<UserInfoResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                userService.getInfo(accountId)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponse>>> getListAccount(){

        ApiResponse<List<AccountResponse>> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.getListAccount()
        );

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/accounts/{id}")
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

    @PutMapping("/accounts/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>>  updateAccount(@PathVariable Long id,
                                                             @RequestBody AccountRequest accountRequest){

        ApiResponse<AccountResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.updateAccount(id, accountRequest)
        );

        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
