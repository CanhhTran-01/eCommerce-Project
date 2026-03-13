package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.dto.request.LogoutRequest;
import com.myproject.ecommerce.dto.request.RefreshTokenRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.dto.response.IntrospectResponse;
import com.myproject.ecommerce.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "login")
    @SecurityRequirements
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest req) {

        var authenticationData = authenticationService.authenticate(req);
        return ResponseEntity.ok(new ApiResponse<>(true, null, authenticationData));
    }

    @Operation(summary = "check token")
    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var introspectData = authenticationService.introspect(request);
        return ResponseEntity.ok(new ApiResponse<>(true, null, introspectData));
    }

    @Operation(summary = "refresh token")
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {

        return ResponseEntity.ok(new ApiResponse<>(true, null, authenticationService.refreshToken(request)));
    }

    @Operation(summary = "logout")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequest logoutRequest)
            throws ParseException, JOSEException {

        authenticationService.logout(logoutRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "social login")
    @SecurityRequirements
    @GetMapping("/social-login")
    public void socialAuthenticate(
            @RequestParam("loginType") String loginType, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        loginType = loginType.trim().toLowerCase();

        String redirectUrl = request.getContextPath() + "/oauth2/authorization/" + loginType;

        response.sendRedirect(redirectUrl);
    }
}
