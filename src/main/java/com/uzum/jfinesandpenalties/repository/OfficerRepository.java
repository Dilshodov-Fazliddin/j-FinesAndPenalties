package com.uzum.jfinesandpenalties.repository;

import com.uzum.jfinesandpenalties.entity.OfficerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface OfficerRepository extends JpaRepository<OfficerEntity,Long> {
    UserDetails findByBudgeNumber(String budgeNumber);
}
