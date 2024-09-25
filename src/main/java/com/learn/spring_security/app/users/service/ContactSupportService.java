package com.learn.spring_security.app.users.service;

import com.learn.spring_security.app.users.dto.ContactSupportReqDto;
import com.learn.spring_security.app.users.entity.ContactSupport;

public interface ContactSupportService {

    ContactSupport save(ContactSupport req);

    ContactSupport raiseIssue(ContactSupportReqDto req);
}
