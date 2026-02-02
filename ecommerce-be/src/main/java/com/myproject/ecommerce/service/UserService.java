package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.UserInfoResponse;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.enums.ErrorCode;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.mapper.UserMapper;
import com.myproject.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // thêm mới customer
    public UserInfoResponse createUser(UserRequest userRequest){
        User user = userMapper.toEntity(userRequest);
        return userMapper.toInfoResponse(userRepository.save(user));

    }

    // take user info from account
    public UserInfoResponse getInfo(Long accountId){
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toInfoResponse(user);
    }

    // lấy ra danh sách customers
    @Transactional(readOnly = true)
    public List<UserInfoResponse> getUserList(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toInfoResponse)
                .toList();
    }

    // update customer
    public UserInfoResponse updateUser(Long id, UserRequest userRequest){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        userMapper.updateCustomer(user, userRequest);
        return userMapper.toInfoResponse(userRepository.save(user));
    }

    // delete customer
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
