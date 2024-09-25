package com.learn.spring_security.app.users.repo;

import com.learn.spring_security.app.users.entity.ContactSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactSupportRepository extends JpaRepository<ContactSupport, Long>, JpaSpecificationExecutor<ContactSupport> {
}
