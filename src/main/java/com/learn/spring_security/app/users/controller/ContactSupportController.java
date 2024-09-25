package com.learn.spring_security.app.users.controller;

import com.learn.spring_security.app.users.dto.ContactSupportReqDto;
import com.learn.spring_security.app.users.entity.ContactSupport;
import com.learn.spring_security.app.users.service.ContactSupportService;
import com.learn.spring_security.utils.ApiResponse;
import com.learn.spring_security.utils.ApiResponseModel;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactSupportController {

    private final ContactSupportService contactSupportService;

    public ContactSupportController(ContactSupportService contactSupportService) {
        this.contactSupportService = contactSupportService;
    }

    @PostMapping("/raise")
    public ResponseEntity<ApiResponseModel> createIssueExternal(@RequestBody ContactSupportReqDto req) {
        if (req.getEmail().isBlank() || req.getEmail().isEmpty())
            return ApiResponse.badRequestWithReason("Email is required");
        if (req.getFullName().isBlank() || req.getFullName().isEmpty())
            return ApiResponse.badRequestWithReason("Full Name is required");
        if (req.getMessage().isBlank() || req.getMessage().isEmpty())
            return ApiResponse.badRequestWithReason("Message is required");

        req.setInternalIssue(false);

        try {
            ContactSupport saved = contactSupportService.raiseIssue(req);
            if (saved == null) throw new Exception("");
            return ApiResponse.success(HttpStatus.CREATED, "Request submitted successfully", req.getEmail());
        } catch (Exception e) {
            return ApiResponse.errorWithStatusAndMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot submit your request at the moment due to some unexpected error. Please try again later");
        }
    }

}
