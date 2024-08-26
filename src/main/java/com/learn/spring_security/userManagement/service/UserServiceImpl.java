package com.learn.spring_security.userManagement.service;

import com.learn.spring_security.userManagement.entity.User;
import com.learn.spring_security.userManagement.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User createUser(User user) {
        return userRepo.save(user);
    }
}
