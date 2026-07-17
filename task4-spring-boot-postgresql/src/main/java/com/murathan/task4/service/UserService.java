package com.murathan.task4.service;

import com.murathan.task4.dto.UserRequest;
import com.murathan.task4.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UserRequest request);

    void deleteUser(Long id);
}
