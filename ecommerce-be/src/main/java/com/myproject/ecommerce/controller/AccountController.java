package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.ResetPasswordRequest;
import com.myproject.ecommerce.dto.request.SignUpRequest;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;


    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> createAccount(
            @RequestBody @Valid SignUpRequest signUpRequest
    ){

        accountService.createAccount(signUpRequest);
        var apiResponse = new ApiResponse<Void>(
                true,
                null,
                null
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/me/info")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserInfo(@AuthenticationPrincipal Jwt jwt){
        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT

        ApiResponse<UserInfoResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                userService.getInfo(accountId)
        );

        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AccountInfoResponse>> getAccount(@AuthenticationPrincipal Jwt jwt){

        Long accountId = jwt.getClaim("accountId");  // get account_id from JWT
        ApiResponse<AccountInfoResponse> apiResponse = new ApiResponse<>(
                true,
                null,
                accountService.getAccountInfo(accountId)
        );

        return ResponseEntity.ok(apiResponse);
    }


    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetAccountPass(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody ResetPasswordRequest request
    ){

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        accountService.resetAccountPass(accountId, request);

        var apiResponse = new ApiResponse<Void>(
                true,
                "Mật khẩu mới đã được cập nhật!",
                null
        );
        return ResponseEntity.ok(apiResponse);
    }

}
