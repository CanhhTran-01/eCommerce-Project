package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.LogoutRequest;
import com.myproject.ecommerce.entity.AccountEntity;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtService {

    @Value("${jwt.signerKey}")
    private String signerKey;

    public String generateToken(AccountEntity accountEntity){
        // header
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        // payload
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountEntity.getUsername())
                .issuer("auth-service")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(accountEntity))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        // signature
        try {
            jwsObject.sign(new MACSigner(signerKey.getBytes()));
            return jwsObject.serialize();

        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    // check token
    public JWTClaimsSet verifyToken(String token) throws ParseException, JOSEException {

        JWSVerifier verifier = new MACVerifier(signerKey.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        var verified = signedJWT.verify(verifier);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if ( !(verified && expiryTime.after(new Date())) )
            throw new BaseException(ErrorCode.UNAUTHORIZED);

        return signedJWT.getJWTClaimsSet();

    }


    private String buildScope(AccountEntity accountEntity) {
        return accountEntity.getAccountRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.joining(" "));
    }
}
