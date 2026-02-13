package com.uzum.jfinesandpenalties.component.adapter;

import com.uzum.jfinesandpenalties.constant.Constant;
import com.uzum.jfinesandpenalties.dto.response.GcpResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class GcpAdapter {

    final RestClient restClient;

    @Value(value = "${url.j-gcp.GET_USER_URL}")
    String GET_USER_URL;

    public GcpResponse getUser(String personalIdentificationNumber) {
        return restClient.get()
                .uri(GET_USER_URL +"/{personalIdentificationNumber}", personalIdentificationNumber)
                .retrieve()
                .body(GcpResponse.class);
    }
}
