package com.learn.spring_security.app.general.controller;


import com.learn.spring_security.app.general.service.GeneralService;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/general")
public class GeneralController {

    private final GeneralService generalService;

    public GeneralController(GeneralService generalService) {
        this.generalService = generalService;
    }

    @GetMapping("/get-roles")
    public ResponseEntity<ApiResponseModel> createUsers() {
        return ApiResponse.success(HttpStatus.OK,"Fetched role types successfully", generalService.getRoleTypes());
    }
}
