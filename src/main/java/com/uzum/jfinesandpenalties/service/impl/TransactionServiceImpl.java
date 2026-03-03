package com.uzum.jfinesandpenalties.service.impl;

import com.uzum.jfinesandpenalties.component.adapter.BankAdapter;
import com.uzum.jfinesandpenalties.component.kafka.producer.KafkaFineProducer;
import com.uzum.jfinesandpenalties.constant.enums.DecisionStatus;
import com.uzum.jfinesandpenalties.constant.enums.error.ErrorType;
import com.uzum.jfinesandpenalties.dto.event.TransactionEvent;
import com.uzum.jfinesandpenalties.dto.request.PaymentRequest;
import com.uzum.jfinesandpenalties.dto.response.TransactionResponse;
import com.uzum.jfinesandpenalties.entity.DecisionEntity;
import com.uzum.jfinesandpenalties.exception.ApplicationException;
import com.uzum.jfinesandpenalties.mapper.TransactionMapper;
import com.uzum.jfinesandpenalties.repository.TransactionRepository;
import com.uzum.jfinesandpenalties.service.DecisionService;
import com.uzum.jfinesandpenalties.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    BankAdapter bankAdapter;
    TransactionMapper transactionMapper;
    DecisionService decisionService;
    KafkaFineProducer kafkaFineProducer;
    TransactionRepository transactionRepository;

    @Override
    @Transactional
    public TransactionResponse create(PaymentRequest paymentRequest) {

        var decisionEntity = decisionService.findById(paymentRequest.decisionId());

        validateAmount(Double.valueOf(paymentRequest.amount()), decisionEntity.getFineAmount());

        validateDecision(decisionEntity);

        var transactionRequest = transactionMapper.toRequest(paymentRequest);

        var transaction = bankAdapter.createTransaction(transactionRequest);

        decisionEntity.setDecisionStatus(DecisionStatus.PAID);

        log.info("Decision status changed for id: {}", decisionEntity.getId());

        publishTransactionEvent(transaction);

        log.info("transaction published to topic {}", transaction);

        return transaction;

    }

    @Override
    public void saveTransaction(TransactionEvent transactionEvent) {
        var entity = transactionMapper.toEntity(transactionEvent);
        transactionRepository.save(entity);
    }


    public void publishTransactionEvent(TransactionResponse transactionResponse){
        TransactionEvent build = TransactionEvent.builder()
                .transactionId(transactionResponse.id())
                .status(transactionResponse.status())
                .amount(transactionResponse.amount())
                .currency(transactionResponse.currency())
                .referenceId(transactionResponse.referenceId())
                .build();
        kafkaFineProducer.publishForFinePaidEvent(build);
    }

    private void validateAmount(Double amount, Double fineAmount) {
        if (!amount.equals(fineAmount)) {
            throw new ApplicationException(
                    10012,
                    "Incorrect payment amount",
                    ErrorType.VALIDATION,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    private void validateDecision(DecisionEntity decision) {

        if (decision.getDecisionStatus() != DecisionStatus.UN_PAID) {
            throw new ApplicationException(
                    10012,
                    "Your fine is already paid",
                    ErrorType.VALIDATION,
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
