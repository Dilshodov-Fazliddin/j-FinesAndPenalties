package com.uzum.jfinesandpenalties.controller;

import com.uzum.jfinesandpenalties.dto.request.PaymentRequest;
import com.uzum.jfinesandpenalties.dto.response.TransactionResponse;
import com.uzum.jfinesandpenalties.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fines-penalties/transaction")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TransactionController {

    TransactionService transactionService;

    @PostMapping("/pay")
    public ResponseEntity<TransactionResponse>create(@RequestBody @Valid PaymentRequest paymentRequest){
        TransactionResponse transactionResponse = transactionService.create(paymentRequest);
        return ResponseEntity.ok(transactionResponse);
    }
}
