package com.myproject.ecommerce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.security.jwt.JwtHandler;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import java.text.ParseException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtHandlerTest {

    private final JwtHandler jwtHandler = new JwtHandler();

    private static final String FAKE_SIGNER_KEY = "a44pQCuzSGIvoCTH5c8qT8QdkWT/9f+dJwChREfqKAA=";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(jwtHandler, "signerKey", FAKE_SIGNER_KEY);
        ReflectionTestUtils.setField(jwtHandler, "VALID_DURATION", 3600L);
        ReflectionTestUtils.setField(jwtHandler, "REFRESHABLE_DURATION", 86400L);
    }

    private Account buidAccount() {
        return Account.builder()
                .id(1L)
                .username("canhtran")
                .accountRoles(Set.of(Role.USER))
                .build();
    }

    @Test
    void success_shouldReturnCorrectClaims() throws ParseException, JOSEException {

        String token = jwtHandler.generateToken(buidAccount());
        JWTClaimsSet claimsSet = jwtHandler.verifyToken(token, false);

        assertThat(claimsSet.getSubject()).isEqualTo("canhtran");
        assertThat(claimsSet.getLongClaim("accountId")).isEqualTo(1L);
    }

    @Test
    void expiredToken_shouldThrow() {
        ReflectionTestUtils.setField(jwtHandler, "VALID_DURATION", 0L);

        String token = jwtHandler.generateToken(buidAccount());

        assertThrows(BaseException.class, () -> jwtHandler.verifyToken(token, false));
    }

    @Test
    void signatureIncorrect_shouldThrow() {
        String fakeToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huV2ljayJ9.INVALID_SIGNATURE";

        assertThrows(BaseException.class, () -> jwtHandler.verifyToken(fakeToken, false));
    }
}
