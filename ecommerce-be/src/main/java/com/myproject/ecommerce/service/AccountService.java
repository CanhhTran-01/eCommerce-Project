package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.AccountRequest;
import com.myproject.ecommerce.dto.response.AccountResponse;
import com.myproject.ecommerce.entity.CustomerEntity;
import com.myproject.ecommerce.entity.AccountEntity;
import com.myproject.ecommerce.mapper.AccountEntityMapper;
import com.myproject.ecommerce.repository.AccountRepository;
import com.myproject.ecommerce.utils.CustomerNameRandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountEntityMapper accountEntityMapper;

    public AccountResponse creataUserAccount(AccountRequest accountRequest){

        if (accountRepository.existsByUsername(accountRequest.getUsername())){
            throw new RuntimeException("Username existed !");
        }

        // MapStruct convert DTO->Entity
        AccountEntity accountEntity = accountEntityMapper.toEntity(accountRequest);

        // set tay Customer (tạo acc sẽ tạo Customer mặc định)
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setFullName(CustomerNameRandomUtils.generateDefaultName());

        accountEntity.setCustomerEntity(customerEntity);
        customerEntity.setAccountEntity(accountEntity);

        accountRepository.save(accountEntity); // nếu cascade chưa set -> Hibernate tự động throw Exception
        return accountEntityMapper.toResponse(accountEntity);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getListAccount(){
        return accountRepository.findAll()
                .stream()
                .map(accountEntityMapper::toResponse)
                .toList();
    }

    public AccountResponse getUserAccount(Long id){
        return accountEntityMapper.toResponse(accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException ("User not found !")) );
    }

    public AccountResponse updateAccount(Long id, AccountRequest accountRequest){
        AccountEntity accountEntity = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found !"));

        // dùng map struct thay vì phải map bằng tay
        accountEntityMapper.updateAccount(accountEntity, accountRequest);
        return accountEntityMapper.toResponse(accountRepository.save(accountEntity));
    }

    // delete acc
    public void deleteUserAccount(Long id){
        accountRepository.deleteById(id);
    }
}
