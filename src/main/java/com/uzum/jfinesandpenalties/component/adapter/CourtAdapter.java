package com.uzum.jfinesandpenalties.component.adapter;

import com.uzum.jfinesandpenalties.constant.Constant;
import com.uzum.jfinesandpenalties.dto.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class CourtAdapter {
    final RestClient restClient;

    @Value(value = "${url.j-court.ARTICLE_URL}")
    String ARTICLE_URL;

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
}
