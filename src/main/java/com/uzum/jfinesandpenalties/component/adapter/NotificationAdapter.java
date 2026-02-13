package com.uzum.jfinesandpenalties.component.adapter;

import com.uzum.jfinesandpenalties.constant.Constant;
import com.uzum.jfinesandpenalties.dto.request.NotificationEmailRequest;
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

    @Value(value = "${url.j-notification.SEND_EMAIL_URL}")
    String EMAIL_URL;

    @Value(value = "${url.j-notification.MERCHANT_LOGIN}")
    String MERCHANT_LOGIN;

    @Value(value = "${url.j-notification.MERCHANT_PASSWORD}")
    String MERCHANT_PASSWORD;

    @Value(value = "${url.j-notification.MERCHANT_ID}")
    String MERCHANT_ID;


    public void sendNotificationEmail(String message,String email){
        restClient.post()
                .uri(EMAIL_URL + "/" + MERCHANT_ID)
                .headers(headers->{
                    headers.setBasicAuth(MERCHANT_LOGIN,MERCHANT_PASSWORD);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .body(new NotificationEmailRequest(message,email))
                .retrieve()
                .toBodilessEntity();

        log.info("Notification sent to {}", email );
    }
}
