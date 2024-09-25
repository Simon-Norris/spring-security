package com.learn.spring_security.app.users.enums;

import lombok.Getter;

@Getter
public enum ContactSupportStatus {

    ISSUED("ISSUED", "ISSUED RAISED"),
    REVIEW_PENDING("REVIEW_PENDING", "ISSUE UNDER REVIEW"),
    REVIEW_COMPLETED("REVIEW_COMPLETED", "ISSUE COMPLETED"),
    REVIEW_RETURNED("REVIEW_RETURNED", "ISSUE RETURNED"),
    REVIEW_REJECTED("REVIEW_REJECTED", "ISSUE REJECTED");

    private final String key;
    private final String value;
    ContactSupportStatus(String value, String key) {
        this.value = value;
        this.key = key;
    }
}
