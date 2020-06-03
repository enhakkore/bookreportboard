package com.practice.bookreportboard.domain.books.kakao;

import com.practice.bookreportboard.domain.books.naver.NaverBooks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.*;

@Component
public class KakaoBookRestTemplate {

    @Value("${kakao.host}")
    private String host;

    @Value("${kakao.path}")
    private String path;

    @Value("${kakao.secret}")
    private String CLIENT_SECRET;

    private RestTemplate restTemplate;
    private HttpEntity<NaverBooks> requestEntity;
    private List<ClientHttpRequestInterceptor> clientHttpRequestInterceptorList;
    private UriComponentsBuilder uriComponentsBuilder;
    private String method;

    @PostConstruct
    private void postConstruct() {
        initializeRestTemplate();
        initializeRequestEntity();
        initializeClientHttpRequestInterceptorList();
        initializeUriComponentBuilder();
    }

    private void initializeRestTemplate() {
        restTemplate = new RestTemplate();
    }

    private void initializeRequestEntity() {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("Authorization", CLIENT_SECRET);
        headerMap.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headerMap);
    }

    private void initializeClientHttpRequestInterceptorList() {
        Assert.notNull(restTemplate, "restTemplate must not be null.");
        clientHttpRequestInterceptorList = restTemplate.getInterceptors();
    }

    private void initializeUriComponentBuilder() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https").host(host).path(path);
    }

    public KakaoBookRestTemplate addInterceptor(ClientHttpRequestInterceptor clientHttpRequestInterceptor){
        Assert.notNull(clientHttpRequestInterceptorList, "clientHttpRequestInterceptorList must not be null");
        clientHttpRequestInterceptorList.add(clientHttpRequestInterceptor);
        return this;
    }

    public KakaoBookRestTemplate setQuery(String target, String query){
        uriComponentsBuilder.replaceQueryParam("target", target)
                            .replaceQueryParam("query", query);
        return this;
    }

    public KakaoBookRestTemplate setMethod(String method){
        this.method = method;
        return this;
    }

    public KakaoBookRestTemplate setErrorHandler(ResponseErrorHandler responseErrorHandler){
        restTemplate.setErrorHandler(responseErrorHandler);
        return this;
    }

    public ResponseEntity<KakaoBooks> exchange(){
        URI uri = uriComponentsBuilder.encode().build().toUri();
        return restTemplate.exchange(uri, getHttpMethod(), requestEntity, KakaoBooks.class);
    }

    public HttpMethod getHttpMethod() {
        return HttpMethod.resolve(method);
    }

    public HttpRequest getHttpRequest(){
        KakaoBookRestTemplate restTemplate = this;
        return new HttpRequest() {
            @Override
            public String getMethodValue() {
                return restTemplate.method;
            }

            @Override
            public URI getURI() {
                return restTemplate.uriComponentsBuilder.encode().build().toUri();
            }

            @Override
            public HttpHeaders getHeaders() {
                return restTemplate.requestEntity.getHeaders();
            }
        };
    }
}
