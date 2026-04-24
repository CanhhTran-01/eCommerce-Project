package com.myproject.ecommerce.unit;

import com.myproject.ecommerce.dto.request.AuthenticationRequest;
import com.myproject.ecommerce.dto.request.RefreshTokenRequest;
import com.myproject.ecommerce.dto.response.AuthenticationResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.repository.InvalidatedTokenRepository;
import com.myproject.ecommerce.security.jwt.JwtHandler;
import com.myproject.ecommerce.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private JwtHandler jwtHandler;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private InvalidatedTokenRepository invalidatedTokenRepository;

    @Mock
    private StringRedisTemplate stringRedisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(authenticationService, "REFRESHABLE_DURATION", 3600L);
        lenient().when(stringRedisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void authenticate_accountNotFound_shouldThrow() {
        when(stringRedisTemplate.hasKey("login_lock:canhtran")).thenReturn(false);

        when(accountRepository.findByUsername("canhtran")).thenReturn(Optional.empty());

        BaseException exception = assertThrows(
                BaseException.class,
                () -> authenticationService.authenticate(AuthenticationRequest.builder()
                        .username("canhtran")
                        .password("demo@123")
                        .build()));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USERNAME_INVALID);

        verify(jwtHandler, never()).generateToken(any());
    }

    @Test
    void authenticate_passwordInvalid_shouldThrow() {

        when(stringRedisTemplate.hasKey("login_lock:canhtran")).thenReturn(false);
        when(valueOperations.increment("login_attempt:canhtran")).thenReturn(1L);

        Account account =
                Account.builder().username("canhtran").password("pass").build();

        when(accountRepository.findByUsername("canhtran")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("wrongPass", "pass")).thenReturn(false);

        BaseException exception = assertThrows(
                BaseException.class,
                () -> authenticationService.authenticate(AuthenticationRequest.builder()
                        .username("canhtran")
                        .password("wrongPass")
                        .build()));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.PASSWORD_INVALID);

        verify(jwtHandler, never()).generateToken(any());
    }

    @Test
    void authenticate_success_shouldReturnToken() {

        when(stringRedisTemplate.hasKey("login_lock:canhtran")).thenReturn(false);

        Account account =
                Account.builder().username("canhtran").password("hasedPass").build();

        when(accountRepository.findByUsername("canhtran")).thenReturn(Optional.of(account));
        when(passwordEncoder.matches("pass", "hasedPass")).thenReturn(true);
        when(jwtHandler.generateToken(account)).thenReturn("fakeToken");

        AuthenticationResponse response = authenticationService.authenticate(AuthenticationRequest.builder()
                .username("canhtran")
                .password("pass")
                .build());

        assertThat(response.getToken()).isEqualTo("fakeToken");
        assertThat(response.isAuthenticated()).isTrue();

        verify(jwtHandler).generateToken(account); // generate token only once
    }

    @Test
    void refreshToken_tokenAlreadyInvalidated_shouldThrow() throws ParseException, JOSEException {
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID("ct080107")
                .issueTime(new Date())
                .subject("canhtran")
                .build();

        when(jwtHandler.verifyToken("oldToken", true)).thenReturn(claimsSet);
        when(invalidatedTokenRepository.existsById("ct080107")).thenReturn(true);

        BaseException exception = assertThrows(
                BaseException.class,
                () -> authenticationService.refreshToken(
                        RefreshTokenRequest.builder().token("oldToken").build()));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.UNAUTHENTICATED);

        verify(invalidatedTokenRepository, never()).save(any());
        verify(jwtHandler, never()).generateToken(any());
    }

    @Test
    void refreshToken_wrongToken_shouldThrow() throws ParseException, JOSEException {
        when(jwtHandler.verifyToken("wrongToken", true)).thenThrow(new BaseException(ErrorCode.UNAUTHENTICATED));

        assertThrows(
                BaseException.class,
                () -> authenticationService.refreshToken(
                        RefreshTokenRequest.builder().token("wrongToken").build()));

        verify(invalidatedTokenRepository, never()).existsById(any());
        verify(invalidatedTokenRepository, never()).save(any());
        verify(jwtHandler, never()).generateToken(any());
    }

    @Test
    void refreshToken_succes_shouldReturnNewToken() throws ParseException, JOSEException {
        Account account = Account.builder().username("canhtran").build();

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .jwtID("ct080107")
                .issueTime(new Date())
                .subject("canhtran")
                .build();

        when(jwtHandler.verifyToken("oldToken", true)).thenReturn(claimsSet);
        when(invalidatedTokenRepository.existsById("ct080107")).thenReturn(false);
        when(accountRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(account));
        when(jwtHandler.generateToken(account)).thenReturn("newToken");

        AuthenticationResponse response = authenticationService.refreshToken(
                RefreshTokenRequest.builder().token("oldToken").build());

        assertThat(response.getToken()).isEqualTo("newToken");
        assertThat(response.isAuthenticated()).isTrue();

        verify(invalidatedTokenRepository)
                .save(argThat(invalidToken -> invalidToken.getId().equals("ct080107")));
        verify(jwtHandler).generateToken(account);
    }
}
