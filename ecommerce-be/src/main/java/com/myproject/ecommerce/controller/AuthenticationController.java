package com.myproject.ecommerce.controller;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.dto.request.LogoutRequest;
import com.myproject.ecommerce.dto.response.ApiResponse;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.dto.response.IntrospectResponse;
import com.myproject.ecommerce.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest req){

        var authenticationData = authenticationService.authenticate(req);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                null,
                authenticationData
        ));
    }

    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse<IntrospectResponse>> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var introspectData = authenticationService.introspect(request);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                null,
                introspectData
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestBody LogoutRequest logoutRequest)
            throws ParseException, JOSEException {

        authenticationService.logout(logoutRequest);
        return ResponseEntity.noContent().build();
    }
}
