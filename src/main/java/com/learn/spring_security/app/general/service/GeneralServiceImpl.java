package com.learn.spring_security.app.general.service;

import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.config.security.SecurityUser;
import com.learn.spring_security.utils.UtilService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GeneralServiceImpl implements GeneralService {
    @Override
    public Set<RoleType> getRoleTypes() {

        try {
            SecurityUser securityUser = UtilService.loggedInUser();
            Set<RoleType> roleTypeSet = securityUser.getUser().getRoles().stream().map(Role::getName).collect(Collectors.toSet());

            if (roleTypeSet.contains(RoleType.SUPER_ADMIN)) {
                return new HashSet<>(List.of(RoleType.values()));
            } else if (roleTypeSet.contains(RoleType.ADMIN) || roleTypeSet.contains(RoleType.EDITOR)) {
                return Stream.of(RoleType.values())
                        .filter(roleType -> !roleType.equals(RoleType.SUPER_ADMIN)).collect(Collectors.toSet());
            } else return Collections.emptySet();
        } catch (UsernameNotFoundException e) {
            throw new IllegalArgumentException("Role Types fetch unsuccessful");
        }
    }
}
