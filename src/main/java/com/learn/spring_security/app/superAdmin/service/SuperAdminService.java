package com.learn.spring_security.app.superAdmin.service;

import com.learn.spring_security.app.superAdmin.dto.UserRegisterDto;
import com.learn.spring_security.utils.ApiResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@PreAuthorize("hasRole('SUPER_ADMIN')")
public interface SuperAdminService {

    ResponseEntity<ApiResponseModel> createUser(UserRegisterDto req);

}
