package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.*;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.*;
import com.myproject.ecommerce.exception.BaseException;
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
        // check email
        if (accountRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BaseException(ErrorCode.EMAIL_EXISTED);
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

        // MapStruct convert DTO -> Entity
        Account account = accountMapper.toEntity(registerRequest);

        // set password
        account.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // set roles
        Set<Role> accountRoles = new HashSet<>();
        accountRoles.add(Role.USER); // setting default role when account is created
        account.setAccountRoles(accountRoles);

        // set Status
        account.setAccountStatus(AccountStatus.ACTIVE);

        // set Provider
        account.setAuthProvider(AuthProvider.LOCAL);

        // set default user (created user automatically)
        User user = new User();
        user.setGender(Gender.HIDE);
        user.setNickName(NickNameRandomUtils.generateDefaultNickName());
        user.setUserCode(UserCodeRandomUtils.generateUserCode());

        account.setUser(user);
        user.setAccount(account);

        accountRepository.save(account); // without cascade -> Hibernate throw Exception
    }

    // forgot account password
    public void forgotPassword(ForgotPasswordRequest request) {

        Account account = accountRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new BaseException(ErrorCode.USERNAME_NOT_FOUND));

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

        // check old password
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())) {
            throw new BaseException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        // check OTP flag for verifying
        otpService.ensureOtpVerified(account.getEmail(), request.getOtpType());

        otpService.clearVerify(account.getEmail(), request.getOtpType()); // verify sucessfully

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    // get account info
    @Transactional(readOnly = true)
    public AccountInfoResponse getAccountInfo(Long id) {
        return accountMapper.toInfoResponse(
                accountRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND)));
    }
}
