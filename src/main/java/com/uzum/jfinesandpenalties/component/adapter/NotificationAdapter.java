package com.uzum.jfinesandpenalties.component.adapter;

import com.uzum.jfinesandpenalties.dto.request.NotificationEmailRequest;
import com.uzum.jfinesandpenalties.dto.request.Receiver;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class NotificationAdapter {

    final RestClient restClient;

    @Value("${url.j-notification.MERCHANT_LOGIN}")
    String merchantLogin;
    @Value("${url.j-notification.MERCHANT_PASSWORD}")
    String merchantPassword;
    @Value("${url.j-notification.SEND_EMAIL_URL}")
    String url;


    public void sendNotification(String message,String email) {
        var result = restClient.post()
                .uri(url)
                .headers(headers->{
                    headers.setBasicAuth(merchantLogin,merchantPassword);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .body(new NotificationEmailRequest(new Receiver(email),"EMAIL",message))
                .retrieve()
                .toBodilessEntity();

        log.info("{}", result);

    }

}
