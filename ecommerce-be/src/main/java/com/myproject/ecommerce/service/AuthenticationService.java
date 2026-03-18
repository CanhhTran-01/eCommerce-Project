package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.dto.request.LogoutRequest;
import com.myproject.ecommerce.dto.request.RefreshTokenRequest;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.dto.response.IntrospectResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.InvalidToken;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.InvalidatedTokenRepository;
import com.myproject.ecommerce.security.jwt.JwtService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    // login
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        // check account exists
        Account account = accountRepository
                .findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        // check username
        if (!account.getUsername().equals(authenticationRequest.getUsername())) {
            throw new BaseException(ErrorCode.USERNAME_INVALID);
        }

        // check password
        if (!passwordEncoder.matches(authenticationRequest.getPassword(), account.getPassword())) {
            throw new BaseException(ErrorCode.PASSWORD_INVALID);
        }

        // get token
        String token = jwtService.generateToken(account);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    // introspect token
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        JWTClaimsSet jwtClaimsSet = jwtService.verifyToken(introspectRequest.getToken());

        // exist      (repo return true)  -> isValid = false
        // not exist  (repo return false) -> isValid = true
        boolean isValid = !(invalidatedTokenRepository.existsById(jwtClaimsSet.getJWTID()));

        return IntrospectResponse.builder().valid(isValid).build();
    }

    // refresh token
    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {

        // verify
        var jwtClaimsSet = jwtService.verifyToken(request.getToken());

        var jti = jwtClaimsSet.getJWTID();
        if (invalidatedTokenRepository.existsById(jti)) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED);
        }
        var expiryTime = jwtClaimsSet.getExpirationTime();

        // logout
        InvalidToken invalidToken =
                InvalidToken.builder().id(jti).expiryTime(expiryTime).build();
        invalidatedTokenRepository.save(invalidToken);

        // generate new token
        var username = jwtClaimsSet.getSubject();
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new BaseException(ErrorCode.UNAUTHENTICATED));
        String token = jwtService.generateToken(account);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    // logout
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {

        JWTClaimsSet jwtClaimsSet = jwtService.verifyToken(logoutRequest.getToken());

        InvalidToken invalidToken = InvalidToken.builder()
                .id(jwtClaimsSet.getJWTID())
                .expiryTime(jwtClaimsSet.getExpirationTime())
                .build();

        invalidatedTokenRepository.save(invalidToken);
    }
}
