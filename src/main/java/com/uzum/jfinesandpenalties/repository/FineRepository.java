package com.uzum.jfinesandpenalties.repository;

import com.uzum.jfinesandpenalties.entity.FineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FineRepository extends JpaRepository<FineEntity,Long> {
}
