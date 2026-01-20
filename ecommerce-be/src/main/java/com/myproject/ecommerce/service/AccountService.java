package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.entity.UserEntity;
import com.myproject.ecommerce.entity.AccountEntity;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.AccountMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.UserNameRandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountResponse createAccount(AccountRequest accountRequest){

        if (accountRepository.existsByUsername(accountRequest.getUsername())){
            throw new BaseException(ErrorCode.USERNAME_EXISTED);
        }

        // MapStruct convert DTO->Entity
        AccountEntity accountEntity = accountMapper.toEntity(accountRequest);

        // set mat khau
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        accountEntity.setPassword(passwordEncoder.encode(accountRequest.getPassword()));

        // set tay Customer (tạo acc sẽ tạo Customer mặc định)
        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(UserNameRandomUtils.generateDefaultName());

        accountEntity.setUserEntity(userEntity);
        userEntity.setAccountEntity(accountEntity);

        accountRepository.save(accountEntity); // nếu cascade chưa set -> Hibernate tự động throw Exception
        return accountMapper.toResponse(accountEntity);
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
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() ->  new BaseException (ErrorCode.ACCOUNT_NOT_FOUND));

        // dùng map struct thay vì phải map bằng tay
        accountMapper.updateAccount(accountEntity, accountRequest);
        return accountMapper.toResponse(accountRepository.save(accountEntity));
    }

    // delete acc
    public void deleteAccount(Long id){
        accountRepository.deleteById(id);
    }
}
