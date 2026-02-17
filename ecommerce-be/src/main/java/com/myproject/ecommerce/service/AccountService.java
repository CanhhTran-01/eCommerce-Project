package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.entity.Account;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.enums.Role;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.AccountMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.UserNameRandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;


    public AccountResponse createAccount(AccountRequest accountRequest){

        if (accountRepository.existsByUsername(accountRequest.getUsername())){
            throw new BaseException(ErrorCode.USERNAME_EXISTED);
        }

        // MapStruct convert DTO->Entity
        Account account = accountMapper.toEntity(accountRequest);

        // set mat khau
        account.setPassword(passwordEncoder.encode(accountRequest.getPassword()));

        // set roles
        Set<Role> accountRoles = new HashSet<>();
        accountRoles.add(Role.USER); // setting default role when account is created
        account.setAccountRoles(accountRoles);

        // set tay Customer (tạo acc sẽ tạo Customer mặc định)
        User user = new User();
        user.setFullName(UserNameRandomUtils.generateDefaultName());

        account.setUser(user);
        user.setAccount(account);

        accountRepository.save(account); // nếu cascade chưa set -> Hibernate tự động throw Exception
        return accountMapper.toResponse(account);
    }


    @Transactional(readOnly = true)
    public List<AccountResponse> getListAccount(){
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }


    public AccountResponse getAccount(Long id){
        return accountMapper.toResponse(accountRepository.findById(id)
                .orElseThrow(() -> new BaseException (ErrorCode.ACCOUNT_NOT_FOUND)));
    }

    
    public AccountResponse updateAccount(Long id, AccountRequest accountRequest){
        Account account = accountRepository.findById(id)
                .orElseThrow(() ->  new BaseException (ErrorCode.ACCOUNT_NOT_FOUND));

        // dùng map struct thay vì phải map bằng tay
        accountMapper.updateAccount(account, accountRequest);
        return accountMapper.toResponse(accountRepository.save(account));
    }

}
