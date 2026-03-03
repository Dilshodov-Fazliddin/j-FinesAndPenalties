package com.uzum.jfinesandpenalties.entity;

import com.uzum.jfinesandpenalties.constant.enums.Rank;
import com.uzum.jfinesandpenalties.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Officer_entity")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class OfficerEntity extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(nullable = false)
    Integer age;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    Rank rank;

    @Column(nullable = false,unique = true)
    String budgeNumber;

    @Column(nullable = false)
    String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return budgeNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}
