package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.dto.request.LogoutRequest;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.dto.response.IntrospectResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.InvalidToken;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.InvalidatedTokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final JwtService jwtService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;


    // login
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){

        Account account = accountRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(()-> new BaseException(ErrorCode.USERNAME_EXISTED));

        // check password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword(),
                                                                account.getPassword());
        if (!authenticated)
            throw new BaseException(ErrorCode.UNAUTHENTICATED);

        // take token
        String token = jwtService.generateToken(account);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }


    // check token
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws ParseException, JOSEException {

        JWTClaimsSet jwtClaimsSet = jwtService.verifyToken(introspectRequest.getToken());

        boolean isValid = !(invalidatedTokenRepository.existsById(jwtClaimsSet.getJWTID()));

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
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
