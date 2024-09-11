package com.learn.spring_security.app.userManagement.service;

import com.learn.spring_security.app.exceptions.RoleNotFoundException;
import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.app.userManagement.entity.Role;
import com.learn.spring_security.app.userManagement.entity.User;
import com.learn.spring_security.app.userManagement.enums.RoleType;

import java.util.Optional;

public interface UserService {

    User createUser(User user);

    Optional<Role> findRoleByType(RoleType roleType);

    Role createRole(RoleType roleType);

    Role updateRole(Role role) throws RoleNotFoundException;

    Optional<User> findUserByName(String name);

    User updateUser(User user) throws UsernameNotFoundException;
}
