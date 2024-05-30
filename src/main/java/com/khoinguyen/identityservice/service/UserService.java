package com.khoinguyen.identityservice.service;

import com.khoinguyen.identityservice.dto.request.UserCreationRequest;
import com.khoinguyen.identityservice.dto.request.UserUpdateRequest;
import com.khoinguyen.identityservice.dto.response.UserResponse;
import com.khoinguyen.identityservice.entity.User;
import com.khoinguyen.identityservice.exception.AppException;
import com.khoinguyen.identityservice.exception.ErrorCode;
import com.khoinguyen.identityservice.mapper.UserMapper;
import com.khoinguyen.identityservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public UserResponse getUser(String userId) {
        return userRepository.findById(userId)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(user);
    }

    public UserResponse deleteUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_EXISTED));
        userRepository.deleteById(user.getId());
        return userMapper.toUserResponse(user);
    }
}
