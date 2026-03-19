package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.*;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.AccountStatus;
import com.myproject.ecommerce.enums.AuthProvider;
import com.myproject.ecommerce.enums.Gender;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.mapper.AccountMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.NickNameRandomUtils;
import com.myproject.ecommerce.utils.UserCodeRandomUtils;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final MailService mailService;

    public void sendRegisterOtp(GenerateOtpRequest request) {
        otpService.generateOtp(request);
    }

    public void sendForgotPassOtp(GenerateOtpRequest request) {
        // check email
        if (accountRepository.findByEmail(request.getEmail()).isEmpty()) {
            throw new BaseException(ErrorCode.EMAIL_NOT_EXISTS);
        }

        otpService.generateOtp(request);
    }

    public void verifyOtp(VerifyOtpRequest request) {
        otpService.verifyOtp(request);
    }

    // register account
    public void createAccount(RegisterRequest registerRequest) {

        if (accountRepository.existsByUsername(registerRequest.getUsername())) {
            throw new BaseException(ErrorCode.USERNAME_EXISTED);
        }

        // handle two case when email existed: local account and oAuth2 account
        // if local account of email request doesn't exist, merge oAuth2 account with local account that is registered
        if (accountRepository.existsByEmail(registerRequest.getEmail())) {
            Account account = accountRepository
                    .findByEmail(registerRequest.getEmail())
                    .orElseThrow(() -> new BaseException(ErrorCode.EMAIL_NOT_FOUND));

            if (account.getAuthProvider().equals(AuthProvider.LOCAL)) {
                throw new BaseException(ErrorCode.EMAIL_EXISTED); // local account of email in request existed
            }

            account.setUsername(registerRequest.getUsername());
            account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            return; // @Transactional support auto commit;
        }

        // roles
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER); // setting role when account is created

        // default user (created user info automatically)
        User user = User.builder()
                .gender(Gender.HIDE)
                .nickName(NickNameRandomUtils.generateDefaultNickName())
                .userCode(UserCodeRandomUtils.generateUserCode())
                .build();

        Account account = Account.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .accountRoles(roles)
                .accountStatus(AccountStatus.ACTIVE)
                .authProvider(AuthProvider.LOCAL)
                .build();

        account.setUser(user);
        user.setAccount(account);

        accountRepository.save(account); // without cascade -> Hibernate throw Exception
    }

    // forgot account password
    public void forgotPassword(ForgotPasswordRequest request) {

        Account account = accountRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new BaseException(ErrorCode.USERNAME_NOT_FOUND));

        if (!account.getAuthProvider().equals(AuthProvider.LOCAL)) {
            throw new BaseException(ErrorCode.NOT_LOCAL_ACCOUNT);
        }

        // check OTP flag for verifying
        otpService.ensureOtpVerified(request.getEmail(), request.getOtpType());

        String newPassword = UUID.randomUUID().toString();
        account.setPassword(passwordEncoder.encode(newPassword));

        otpService.clearVerify(request.getEmail(), request.getOtpType()); // verify sucessfully

        mailService.sendNewPassword(request.getEmail(), request.getUsername(), newPassword);
    }

    // update account password
    public void changeAccountPass(Long accountId, ChangePasswordRequest request) {
        Account account =
                accountRepository.findById(accountId).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!account.getAuthProvider().equals(AuthProvider.LOCAL)) {
            throw new BaseException(ErrorCode.NOT_LOCAL_ACCOUNT);
        }

        // check old password
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new BaseException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        // check OTP flag for verifying
        otpService.ensureOtpVerified(account.getEmail(), request.getOtpType());

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));

        otpService.clearVerify(account.getEmail(), request.getOtpType()); // verify sucessfully
    }

    // get account info
    @Transactional(readOnly = true)
    public AccountInfoResponse getAccountInfo(Long id) {
        return accountMapper.toInfoResponse(
                accountRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND)));
    }
}
