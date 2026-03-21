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
import com.myproject.ecommerce.security.jwt.JwtHandler;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final JwtHandler jwtHandler;
    private final PasswordEncoder passwordEncoder;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Value("${jwt.refreshable-duration}")
    private long REFRESHABLE_DURATION;

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
        String token = jwtHandler.generateToken(account);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    // introspect token
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        JWTClaimsSet jwtClaimsSet = jwtHandler.verifyToken(introspectRequest.getToken(), false);

        // exist      (repo return true)  -> isValid = false
        // not exist  (repo return false) -> isValid = true
        boolean isValid = !(invalidatedTokenRepository.existsById(jwtClaimsSet.getJWTID()));

        return IntrospectResponse.builder().valid(isValid).build();
    }

    // refresh token rotation : pattern 1 token for both access + refresh
    @Transactional
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {

        // verify token first
        var jwtClaimsSet = jwtHandler.verifyToken(request.getToken(), true);

        // check invalid token
        if (invalidatedTokenRepository.existsById(jwtClaimsSet.getJWTID())) {
            throw new BaseException(ErrorCode.UNAUTHENTICATED);
        }

        // make old token invalidated
        // expiry time = issueTime + REFRESHABLE_DURATION, avoid accidentally deleting the token.
        var expiryTime = new Date(jwtClaimsSet
                .getIssueTime()
                .toInstant()
                .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                .toEpochMilli());

        InvalidToken invalidToken = InvalidToken.builder()
                .id(jwtClaimsSet.getJWTID())
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidToken);

        // generate new token
        var username = jwtClaimsSet.getSubject(); // get username from token
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new BaseException(ErrorCode.UNAUTHENTICATED));
        String token = jwtHandler.generateToken(account);

        return AuthenticationResponse.builder().token(token).authenticated(true).build();
    }

    // logout
    public void logout(LogoutRequest logoutRequest) throws ParseException, JOSEException {
        try {
            JWTClaimsSet jwtClaimsSet = jwtHandler.verifyToken(logoutRequest.getToken(), true);

            // expiry time = issueTime + REFRESHABLE_DURATION, avoid accidentally deleting the token.
            var expiryTime = new Date(jwtClaimsSet
                    .getIssueTime()
                    .toInstant()
                    .plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS)
                    .toEpochMilli());

            InvalidToken invalidToken = InvalidToken.builder()
                    .id(jwtClaimsSet.getJWTID())
                    .expiryTime(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidToken);

        } catch (BaseException exception) {
            // hanler case logout with expired token
            log.info("This token already expired !");
        }
    }
}
