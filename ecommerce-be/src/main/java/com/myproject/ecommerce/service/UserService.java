package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
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

    // thêm mới user
    public UserInfoResponse createUser(InfoUpdateRequest infoUpdateRequest){
        User user = userMapper.toEntity(infoUpdateRequest);
        return userMapper.toInfoResponse(userRepository.save(user));

    }

    // take user info from account
    public UserInfoResponse getInfo(Long accountId){
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toInfoResponse(user);
    }

    // lấy ra danh sách user
    @Transactional(readOnly = true)
    public List<UserInfoResponse> getUserList(){
        return userRepository.findAll()
                .stream()
                .map(userMapper::toInfoResponse)
                .toList();
    }

    // update user
    public UserInfoResponse updateUserInfo(Long id, InfoUpdateRequest infoUpdateRequest){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, infoUpdateRequest);
        return userMapper.toInfoResponse(userRepository.save(user));
    }

}
