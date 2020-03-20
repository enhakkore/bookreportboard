package com.practice.bookreportboard.domain.books;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;

@Getter
@Component
public class NaverBookRestTemplate {

    private RestTemplate restTemplate;
    private HttpEntity<NaverBooks> requestEntity;

    @Value("${naver.id}")
    private String CLIENT_ID;

    @Value("${naver.secret}")
    private String CLIENT_SECRET;

    @Value("${naver.host}")
    private String host;

    @Value("${naver.path}")
    private String path;

    @PostConstruct
    public void postConstruct(){
        restTemplate = new RestTemplate();

        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("X-Naver-Client-Id", CLIENT_ID);
        headerMap.add("X-Naver-Client-Secret", CLIENT_SECRET);
        headerMap.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headerMap);
    }

    public ResponseEntity<NaverBooks> search(String bookTitle) throws RestClientException {

        UriComponents tmp = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host(host)
                .path(path)
                .queryParam("d_titl",bookTitle)
                .queryParam("start", "1")
                .queryParam("display","10")
                .encode()
                .build();

        return restTemplate.exchange(tmp.toUri(), HttpMethod.GET, requestEntity, NaverBooks.class);
    }
}
