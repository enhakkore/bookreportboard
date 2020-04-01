package com.practice.bookreportboard.domain.books.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Component
public class KakaoBookRestTemplate {

    private RestTemplate restTemplate;

    private HttpEntity<KakaoBooks> requestEntity;

    @Value("${kakao.host}")
    private String host;

    @Value("${kakao.path}")
    private String path;

    @Value("${kakao.secret}")
    private String CLIENT_SECRET;

    @PostConstruct
    public void postConstruct() {
        restTemplate = new RestTemplate();

        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Authorization", CLIENT_SECRET);
        headerMap.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headerMap);
    }

    public ResponseEntity<KakaoBooks> search(String title){
        UriComponents uri = UriComponentsBuilder.newInstance()
                                    .scheme("https")
                                    .host(host)
                                    .path(path)
                                    .queryParam("target", "title")
                                    .queryParam("query", title)
                                    .encode()
                                    .build();

        return restTemplate.exchange(uri.toUri(), HttpMethod.GET, requestEntity, KakaoBooks.class);
    }

}
