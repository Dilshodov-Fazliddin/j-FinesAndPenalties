package com.uzum.jfinesandpenalties.entity;

import com.uzum.jfinesandpenalties.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity(name = "Transaction_entity")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class TransactionEntity extends BaseEntity {
    @Column(nullable = false)
    UUID transactionId;

    @Column(nullable = false)
    UUID referenceId;

    @Column(nullable = false)
    String status;

    @Column(nullable = false)
    Long amount;

    @Column(nullable = false)
    String currency;
}
