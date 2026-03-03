package com.uzum.jfinesandpenalties.repository;

import com.uzum.jfinesandpenalties.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity,Long> {
}
