package com.learn.spring_security.base.userManagement.entity;

import com.learn.spring_security.base.userManagement.enums.RoleType;
import com.learn.spring_security.base.entity.Auditable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Role extends Auditable {

    @Enumerated(value = EnumType.STRING)
    private RoleType name;


    @Override
    public String toString() {
        return "Role{" +
                "name=" + name +
                '}';
    }
}
