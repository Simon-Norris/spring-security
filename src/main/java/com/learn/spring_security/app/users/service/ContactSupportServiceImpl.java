package com.learn.spring_security.app.users.service;

import com.learn.spring_security.app.users.dto.ContactSupportReqDto;
import com.learn.spring_security.app.users.entity.ContactSupport;
import com.learn.spring_security.app.users.enums.ContactSupportStatus;
import com.learn.spring_security.app.users.repo.ContactSupportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactSupportServiceImpl implements ContactSupportService {

    private final ContactSupportRepository contactSupportRepository;

    public ContactSupportServiceImpl(ContactSupportRepository contactSupportRepository) {
        this.contactSupportRepository = contactSupportRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContactSupport save(ContactSupport req) {
        return this.contactSupportRepository.save(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ContactSupport raiseIssue(ContactSupportReqDto req) {

        ContactSupport contactSupport = ContactSupport.builder()
                .email(req.getEmail())
                .fullName(req.getFullName())
                .issue(req.getMessage())
                .internalIssue(req.isInternalIssue())
                .status(ContactSupportStatus.ISSUED)
                .build();
        return save(contactSupport);
    }
}
