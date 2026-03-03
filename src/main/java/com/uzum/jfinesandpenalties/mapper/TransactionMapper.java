package com.uzum.jfinesandpenalties.mapper;

import com.uzum.jfinesandpenalties.constant.Constant;
import com.uzum.jfinesandpenalties.dto.event.TransactionEvent;
import com.uzum.jfinesandpenalties.dto.request.PaymentRequest;
import com.uzum.jfinesandpenalties.dto.request.TransactionRequest;
import com.uzum.jfinesandpenalties.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.uzum.jfinesandpenalties.constant.Constant.*;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "referenceId", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "type", constant = "P2P")
    @Mapping(target = "currency", constant = "UZS")
    @Mapping(target = "merchantId",  expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "receiverName", constant = RECEIVER_NAME)
    @Mapping(target = "receiverToken", constant = RECEIVER_TOKEN)
    TransactionRequest toRequest(PaymentRequest paymentRequest);

    @Mapping(target = "id", ignore = true)
    TransactionEntity toEntity(TransactionEvent transactionEvent);
}
