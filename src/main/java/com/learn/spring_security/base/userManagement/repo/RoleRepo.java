package com.learn.spring_security.base.userManagement.repo;

import com.learn.spring_security.base.userManagement.entity.Role;
import com.learn.spring_security.base.userManagement.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleType);
}
