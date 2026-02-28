package com.myproject.ecommerce.security.jwt;

import com.myproject.ecommerce.dto.request.IntrospectRequest;
import com.myproject.ecommerce.service.AuthenticationService;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoderCustom implements JwtDecoder {

    @Value("${jwt.signerKey}")
    private String signerKey;

    private final AuthenticationService authenticationService;

    private NimbusJwtDecoder nimbusJwtDecoder;

    @Override
    public Jwt decode(String token) throws JwtException {

        // check valid token
        try {
            var response = authenticationService.introspect(
                    IntrospectRequest.builder().token(token).build());
            if (!response.isValid()) throw new JwtException("JWT invalid !");

        } catch (Exception exception) {
            throw new JwtException(exception.getMessage());
        }

        // decode
        if (Objects.isNull(nimbusJwtDecoder)) {
            SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS256");
            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                    .macAlgorithm(MacAlgorithm.HS256)
                    .build();
        }

        System.out.println(nimbusJwtDecoder);
        return nimbusJwtDecoder.decode(token);
    }
}
