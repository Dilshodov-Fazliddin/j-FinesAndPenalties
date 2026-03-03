package com.uzum.jfinesandpenalties.component.adapter;


import com.uzum.jfinesandpenalties.dto.request.TransactionRequest;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
import com.uzum.jfinesandpenalties.dto.response.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BankAdapter {

    final RestClient restClient;

    @Value(value = "${url.j-bank.TRANSACTION}")
    String url;

    public TransactionResponse createTransaction(TransactionRequest transactionRequest){
        return restClient
                .post()
                .uri(url)
                .body(transactionRequest)
                .retrieve()
                .body(TransactionResponse.class);
    }
}
