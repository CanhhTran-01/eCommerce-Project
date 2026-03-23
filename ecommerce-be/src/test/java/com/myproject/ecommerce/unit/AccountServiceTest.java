package com.myproject.ecommerce.unit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import com.myproject.ecommerce.dto.request.RegisterRequest;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.enums.AuthProvider;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.mapper.AccountMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.service.AccountService;
import com.myproject.ecommerce.service.MailService;
import com.myproject.ecommerce.service.OtpService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private MailService mailService;

    @Mock
    private OtpService otpService;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount_success() {
        // this username does not exist
        when(accountRepository.existsByUsername("johnWick")).thenReturn(false);

        // this email does not exist
        when(accountRepository.existsByEmail("huhuhu@gmail.com")).thenReturn(false);

        // return fake string: "fakePass"
        when(passwordEncoder.encode("demo@123")).thenReturn("fakePass");

        // check not throw
        assertDoesNotThrow(() -> accountService.createAccount(RegisterRequest.builder()
                .email("huhuhu@gmail.com")
                .username("johnWick")
                .password("demo@123")
                .build()));

        verify(accountRepository)
                .save(argThat(account -> account.getUsername().equals("johnWick")
                        && account.getAuthProvider().equals(AuthProvider.LOCAL)
                        && account.getAccountRoles().contains(Role.USER)
                        && account.getPassword().equals("fakePass")));
    }

    @Test
    void createAccount_usernameExisted_shouldThrow() {
        // if username existed
        when(accountRepository.existsByUsername("CanhhTran")).thenReturn(true);

        // throw Exception
        BaseException exception = assertThrows(
                BaseException.class,
                () -> accountService.createAccount(RegisterRequest.builder()
                        .email("huhuhu@gmail.com")
                        .username("CanhhTran")
                        .password("demo@123")
                        .build()));

        // check type of error code
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.USERNAME_EXISTED);

        // save() musn't be invoked
        verify(accountRepository, never()).save(any());
    }

    @Test
    void createAccount_emailExistedAsLocal_shouldThrow() {
        when(accountRepository.existsByUsername("CanhhTran")).thenReturn(false);

        when(accountRepository.existsByEmail("abc@gmail.com")).thenReturn(true);

        when(accountRepository.findByEmail("abc@gmail.com"))
                .thenReturn(Optional.of(
                        Account.builder().authProvider(AuthProvider.LOCAL).build()));

        BaseException exception = assertThrows(
                BaseException.class,
                () -> accountService.createAccount(RegisterRequest.builder()
                        .email("abc@gmail.com")
                        .username("CanhhTran")
                        .password("demo@123")
                        .build()));

        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.EMAIL_EXISTED);

        verify(accountRepository, never()).save(any());
    }

    @Test
    void createAccount_emailExistedAsOauth2_shouldMerge() {
        Account acc = Account.builder().authProvider(AuthProvider.GOOGLE).build();

        when(accountRepository.existsByUsername("CanhhTran")).thenReturn(false);

        when(accountRepository.existsByEmail("abc@gmail.com")).thenReturn(true);

        when(accountRepository.findByEmail("abc@gmail.com")).thenReturn(Optional.of(acc));

        when(passwordEncoder.encode("demo@123")).thenReturn("fakePass");

        accountService.createAccount(RegisterRequest.builder()
                .email("abc@gmail.com")
                .username("CanhhTran")
                .password("demo@123")
                .build());

        assertThat(acc.getUsername()).isEqualTo("CanhhTran");
        assertThat(acc.getPassword()).isEqualTo("fakePass");

        verify(accountRepository, never()).save(any());
    }
}
