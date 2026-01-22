package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.UserRequest;
import com.myproject.ecommerce.dto.response.UserResponse;
import com.myproject.ecommerce.entity.UserEntity;
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
    public UserResponse createUser(UserRequest userRequest){
        UserEntity userEntity = userMapper.toEntity(userRequest);
        return userMapper.toResponse(userRepository.save(userEntity));

    }

    // lấy ra danh sách customers
    @Transactional(readOnly = true)
    public List<UserResponse> getUserList(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    // update customer
    public UserResponse updateUser(Long id, UserRequest userRequest){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        userMapper.updateCustomer(userEntity, userRequest);
        return userMapper.toResponse(userRepository.save(userEntity));
    }

    // delete customer
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
