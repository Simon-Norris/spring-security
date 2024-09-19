package com.learn.spring_security.base.userManagement.service;

import com.learn.spring_security.app.exceptions.RoleNotFoundException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.entity.User;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.userManagement.repo.RoleRepo;
import com.learn.spring_security.base.userManagement.repo.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User createUser(User user) {
        Optional<User> userExists = userRepo.findByUsername(user.getUsername());
        return userExists.orElseGet(() -> userRepo.save(user));
    }

    @Override
    public Optional<Role> findRoleByType(RoleType roleType) {
        return roleRepo.findByName(roleType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role createRole(RoleType roleType) {
        Optional<Role> roleOptional = this.findRoleByType(roleType);
        return  roleOptional.orElseGet(() -> {
            Role role = Role.builder().name(roleType).build();
            return roleRepo.save(role);
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role updateRole(Role role) throws RoleNotFoundException {
        Optional<Role> roleOptional = roleRepo.findByName(role.getName());
        if (roleOptional.isEmpty()) throw new RoleNotFoundException(String.format("Role with name: %s not found", role.getName()));
        return this.roleRepo.save(role);
    }

    @Override
    public Optional<User> findUserByName(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User updateUser(User user) throws UsernameNotFoundException {
        Optional<User> userExists = userRepo.findByUsername(user.getUsername());
        if (userExists.isEmpty()) throw new UsernameNotFoundException(String.format("User with username: %s not found", user.getUsername()));
        return userRepo.save(user);
    }
}
