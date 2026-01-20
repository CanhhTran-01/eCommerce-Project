package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.dto.response.IntrospectResponse;
import com.myproject.ecommerce.entity.AccountEntity;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.GenerateToken;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AccountRepository accountRepository;

    // login
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){

        AccountEntity accountEntity = accountRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(()-> new BaseException(ErrorCode.USERNAME_EXISTED));

        // check password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =  passwordEncoder.matches(authenticationRequest.getPassword(),
                                                                accountEntity.getPassword());
        if (!authenticated)
            throw new BaseException(ErrorCode.UNAUTHENTICATED);

        // take token
        String token = GenerateToken.getToken(authenticationRequest.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // check token
    public IntrospectResponse introspect(IntrospectRequest introspectRequest) throws JOSEException, ParseException {

        String token = introspectRequest.getToken();

        JWSVerifier verifier = new MACVerifier(GenerateToken.SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(verifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }
}
