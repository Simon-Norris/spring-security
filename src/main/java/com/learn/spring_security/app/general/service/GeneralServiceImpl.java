package com.learn.spring_security.app.general.service;

import com.learn.spring_security.app.exceptions.UsernameNotFoundException;
import com.learn.spring_security.app.general.dto.EmailRequest;
import com.learn.spring_security.base.service.email.EmailService;
import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.config.security.SecurityUser;
import com.learn.spring_security.utils.UtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GeneralServiceImpl implements GeneralService {

    private Logger logger = LoggerFactory.getLogger(GeneralServiceImpl.class);
    private final EmailService emailService;

    public GeneralServiceImpl(EmailService emailService) {
        this.emailService = emailService;
    }

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

    @Override
    public boolean testEmail(EmailRequest emailRequest) {
        try {
            emailService.sendSimpleEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
            return true;
        } catch (Exception e) {
            logger.error("::: ERROR :: FAILED TO SEND EMAIL TO {} DUE TO: {} :::", emailRequest.getTo(), e.getMessage());
            return false;
        }
    }
}
