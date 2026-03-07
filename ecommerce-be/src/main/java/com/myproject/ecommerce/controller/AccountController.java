package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.*;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.UserInfoDetailResponse;
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

    @PostMapping("/register/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForSignUp(@RequestBody GenerateOtpRequest request) {

        accountService.sendRegisterOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/forgot-pass/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForForgotPass(@RequestBody GenerateOtpRequest request) {

        accountService.sendForgotAndPassOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/update-pass/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForChangePass(@RequestBody GenerateOtpRequest request) {

        accountService.sendForgotAndPassOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PostMapping("/email/verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody VerifyOtpRequest request) {

        accountService.verifyOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP hợp lệ, xác minh hoàn tất !", null);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> registerNewAccount(@Valid @RequestBody RegisterRequest registerRequest) {

        accountService.createAccount(registerRequest);
        var apiResponse =
                new ApiResponse<Void>(true, "Đăng kí thành công, vui lòng đăng nhập để sử dụng dịch vụ.", null);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/me/info")
    public ResponseEntity<ApiResponse<UserInfoDetailResponse>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        ApiResponse<UserInfoDetailResponse> apiResponse = new ApiResponse<>(true, null, userService.getInfo(accountId));

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AccountInfoResponse>> getAccountInfo(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        ApiResponse<AccountInfoResponse> apiResponse =
                new ApiResponse<>(true, null, accountService.getAccountInfo(accountId));

        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotAccountPassword(@RequestBody ForgotPasswordRequest request) {

        accountService.forgotPassword(request);
        var apiResponse = new ApiResponse<>(true, "Mật khẩu mới đã được gửi tới gmail: " + request.getEmail(), null);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetAccountPassword(
            @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ChangePasswordRequest request) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        accountService.changeAccountPass(accountId, request);

        var apiResponse = new ApiResponse<Void>(true, "Mật khẩu mới đã được cập nhật!", null);
        return ResponseEntity.ok(apiResponse);
    }
}
