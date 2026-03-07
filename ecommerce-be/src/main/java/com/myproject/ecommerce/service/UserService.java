package com.myproject.ecommerce.service;

import com.myproject.ecommerce.dto.request.InfoUpdateRequest;
import com.myproject.ecommerce.dto.response.UserInfoDetailResponse;
import com.myproject.ecommerce.dto.response.UserInfoSummaryResponse;
import com.myproject.ecommerce.entity.User;
import com.myproject.ecommerce.exception.BaseException;
import com.myproject.ecommerce.exception.ErrorCode;
import com.myproject.ecommerce.mapper.UserMapper;
import com.myproject.ecommerce.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // get list user (only for admin)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly = true)
    public List<UserInfoSummaryResponse> getAllUsers() {

        return userRepository.findAll().stream()
                .map(user -> UserInfoSummaryResponse.builder()
                        .id(user.getId())
                        .userCode(user.getUserCode())
                        .avatarUrl(user.getAvatarUrl())
                        .nickName(user.getNickName())
                        .accountRoles(user.getAccount().getAccountRoles())
                        .accountStatus(user.getAccount().getAccountStatus())
                        .build())
                .toList();
    }

    // get user info from account
    public UserInfoDetailResponse getInfo(Long accountId) {
        User user = userRepository
                .findByAccountId(accountId)
                .orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toInfoResponse(user);
    }

    // update user info
    public UserInfoDetailResponse updateUserInfo(Long id, InfoUpdateRequest infoUpdateRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new BaseException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(user, infoUpdateRequest);
        return userMapper.toInfoResponse(userRepository.save(user));
    }
}
