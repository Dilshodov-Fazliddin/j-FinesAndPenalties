package com.uzum.jfinesandpenalties.service;

import com.uzum.jfinesandpenalties.dto.event.TransactionEvent;
import com.uzum.jfinesandpenalties.dto.request.PaymentRequest;
import com.uzum.jfinesandpenalties.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(PaymentRequest paymentRequest);
    void saveTransaction(TransactionEvent event);

}
