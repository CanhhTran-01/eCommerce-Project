package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.*;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.UserInfoDetailResponse;
import com.myproject.ecommerce.service.AccountService;
import com.myproject.ecommerce.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account Controller")
public class AccountController {
    private final AccountService accountService;
    private final UserService userService;

    @Operation(summary = "send Register OTP", description = "Send OTP by email for register feature")
    @SecurityRequirements
    @PostMapping("/register/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForSignUp(@RequestBody GenerateOtpRequest request) {

        accountService.sendRegisterOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "send Forgot Pass OTP")
    @SecurityRequirements
    @PostMapping("/forgot-pass/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForForgotPass(@RequestBody GenerateOtpRequest request) {

        accountService.sendForgotPassOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "send Change Pass OTP")
    @SecurityRequirements
    @PostMapping("/update-pass/email/otp")
    public ResponseEntity<ApiResponse<?>> sendOtpForChangePass(@RequestBody GenerateOtpRequest request) {

        accountService.sendForgotPassOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP đã được gửi đi, " + "vui lòng kiểm tra email của bạn", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "verify OTP")
    @SecurityRequirements
    @PostMapping("/email/verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody VerifyOtpRequest request) {

        accountService.verifyOtp(request);
        var apiResponse = new ApiResponse<>(true, "OTP hợp lệ, xác minh hoàn tất !", null);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "register new Account", description = "submit username + password after verifying OTP")
    @SecurityRequirements
    @PostMapping("")
    public ResponseEntity<ApiResponse<Void>> registerNewAccount(@Valid @RequestBody RegisterRequest registerRequest) {

        accountService.createAccount(registerRequest);
        var apiResponse =
                new ApiResponse<Void>(true, "Đăng kí thành công, vui lòng đăng nhập để sử dụng dịch vụ.", null);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "get User's Profile")
    @GetMapping("/me/info")
    public ResponseEntity<ApiResponse<UserInfoDetailResponse>> getUserInfo(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT

        ApiResponse<UserInfoDetailResponse> apiResponse = new ApiResponse<>(true, null, userService.getInfo(accountId));

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "send Account's Information")
    @GetMapping("/info")
    public ResponseEntity<ApiResponse<AccountInfoResponse>> getAccountInfo(@AuthenticationPrincipal Jwt jwt) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        ApiResponse<AccountInfoResponse> apiResponse =
                new ApiResponse<>(true, null, accountService.getAccountInfo(accountId));

        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "require new Pass", description = "Submit username for reclaiming new password")
    @SecurityRequirements
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotAccountPassword(@RequestBody ForgotPasswordRequest request) {

        accountService.forgotPassword(request);
        var apiResponse = new ApiResponse<>(true, "Mật khẩu mới đã được gửi tới gmail: " + request.getEmail(), null);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update new password")
    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetAccountPassword(
            @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ChangePasswordRequest request) {

        Long accountId = jwt.getClaim("accountId"); // get account_id from JWT
        accountService.changeAccountPass(accountId, request);

        var apiResponse = new ApiResponse<Void>(true, "Mật khẩu mới đã được cập nhật!", null);
        return ResponseEntity.ok(apiResponse);
    }
}
