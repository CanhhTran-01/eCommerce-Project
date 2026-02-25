package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.ResetPasswordRequest;
import com.myproject.ecommerce.dto.request.SignUpRequest;
import com.myproject.ecommerce.dto.response.AccountInfoResponse;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.enums.AccountStatus;
import com.myproject.ecommerce.enums.AuthProvider;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.AccountMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.NickNameRandomUtils;
import com.myproject.ecommerce.utils.UserCodeRandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;


    // register account
    public void createAccount(SignUpRequest signUpRequest){

        if (accountRepository.existsByUsername(signUpRequest.getUsername())){
            throw new BaseException(ErrorCode.USERNAME_EXISTED);
        }

        // MapStruct convert DTO -> Entity
        Account account = accountMapper.toEntity(signUpRequest);

        // set password
        account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

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
        user.setNickName(NickNameRandomUtils.generateDefaultNickName());
        user.setUserCode(UserCodeRandomUtils.generateUserCode());

        account.setUser(user);
        user.setAccount(account);

        accountRepository.save(account); // without cascade -> Hibernate throw Exception
    }


    // get account info
    @Transactional(readOnly = true)
    public AccountInfoResponse getAccountInfo(Long id){
        return accountMapper.toInfoResponse(accountRepository.findById(id)
                .orElseThrow(() -> new BaseException (ErrorCode.ACCOUNT_NOT_FOUND)));
    }


    // update account password
    public void resetAccountPass(Long accountId, ResetPasswordRequest request){
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.ACCOUNT_NOT_FOUND));

        // check old password
        if (!passwordEncoder.matches(request.getOldPassword(), account.getPassword())){
            throw new BaseException(ErrorCode.OLD_PASSWORD_INCORRECT);
        }

        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

}
