package com.learn.spring_security.app.general.service;

import com.learn.spring_security.base.userManagement.enums.RoleType;

import java.util.Set;

public interface GeneralService {

    Set<RoleType> getRoleTypes();
}
