package com.murathan.task5.service.impl;

import com.murathan.task5.entity.User;
import com.murathan.task5.repository.UserRepository;
import com.murathan.task5.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    public User update(Long id, User user) {
        User existing = findById(id);
        existing.setUsername(user.getUsername());
        existing.setPassword(user.getPassword());
        existing.setEmail(user.getEmail());
        return userRepository.save(existing);
    }

    public void delete(Long id) {
        userRepository.delete(findById(id));
    }
}