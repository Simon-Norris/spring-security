package com.learn.spring_security.app.general.controller;


import com.learn.spring_security.app.general.dto.EmailRequest;
import com.learn.spring_security.app.general.service.GeneralService;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/test-email")
    public ResponseEntity<ApiResponseModel> sendEmail(@RequestBody EmailRequest emailRequest) {
        boolean sent = generalService.testEmail(emailRequest);
        String msg = sent ? "Email sent successfully" : "Email not sent";
        return sent ? ApiResponse.successWithStatusAndReason(HttpStatus.OK, msg): ApiResponse.errorWithStatusAndMessage(HttpStatus.EXPECTATION_FAILED, msg);
    }

}
