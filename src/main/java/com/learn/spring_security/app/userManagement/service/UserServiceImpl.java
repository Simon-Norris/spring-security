package com.learn.spring_security.app.userManagement.service;

import com.learn.spring_security.app.userManagement.entity.User;
import com.learn.spring_security.app.userManagement.repo.UserRepo;
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
