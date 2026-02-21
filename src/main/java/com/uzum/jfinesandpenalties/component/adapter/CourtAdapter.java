package com.uzum.jfinesandpenalties.component.adapter;

import com.uzum.jfinesandpenalties.constant.Constant;
import com.uzum.jfinesandpenalties.dto.request.ViolationRequest;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
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
public class CourtAdapter {
    final RestClient restClient;

    @Value(value = "${url.j-court.ARTICLE_URL}")
    String ARTICLE_URL;

    @Value(value = "${url.j-court.VIOLATION_URL}")
    String VIOLATION_URL;

    public ArticleResponse fetchArticleById(Long id){
        return restClient
                .get()
                .uri(ARTICLE_URL + "/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new RuntimeException("Article not found with id: " + id);
                })
                .body(ArticleResponse.class);
    }


    public void createViolationForOffender(ViolationRequest violationRequest){
        var result=restClient.post()
                .uri(VIOLATION_URL)
                .body(violationRequest)
                .retrieve().toBodilessEntity();

        log.info("Violation created {}",result);
    }
}
