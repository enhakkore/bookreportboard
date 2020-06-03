package com.practice.bookreportboard.domain.books.naver;

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
import java.util.List;

@Component
public class NaverBookRestTemplate {

    @Value("${naver.id}")
    private String CLIENT_ID;

    @Value("${naver.secret}")
    private String CLIENT_SECRET;

    @Value("${naver.host}")
    private String host;

    @Value("${naver.path}")
    private String path;

    private RestTemplate restTemplate;
    private HttpEntity<NaverBooks> requestEntity;
    private List<ClientHttpRequestInterceptor> clientHttpRequestInterceptorList;
    private UriComponentsBuilder uriComponentsBuilder;
    private String method;

    @PostConstruct
    private void postConstruct(){
        initializeRestTemplate();
        initializeClientHttpRequestInterceptorList();
        initializeRequestEntity();
        initializeUriComponentBuilder();
    }

    private void initializeRestTemplate() {
        restTemplate = new RestTemplate();
    }

    private void initializeRequestEntity() {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        headerMap.add("X-Naver-Client-Id", CLIENT_ID);
        headerMap.add("X-Naver-Client-Secret", CLIENT_SECRET);
        headerMap.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        requestEntity = new HttpEntity<>(headerMap);
    }

    private void initializeClientHttpRequestInterceptorList() {
        Assert.notNull(restTemplate, "restTemplate must not be null");
        clientHttpRequestInterceptorList = restTemplate.getInterceptors();
    }

    private void initializeUriComponentBuilder() {
        uriComponentsBuilder = UriComponentsBuilder.newInstance()
                .scheme("https").host(host).path(path);
    }

    public NaverBookRestTemplate addInterceptor(ClientHttpRequestInterceptor clientHttpRequestInterceptor){
        Assert.notNull(clientHttpRequestInterceptorList, "clientHttpRequestInterceptorList must not be bull");
        clientHttpRequestInterceptorList.add(clientHttpRequestInterceptor);
        return this;
    }

    public NaverBookRestTemplate setQuery(String title){
        uriComponentsBuilder.replaceQueryParam("d_titl", title);
        return this;
    }

    public NaverBookRestTemplate setMethod(String method){
        this.method = method;
        return this;
    }

    public NaverBookRestTemplate setErrorHandler(ResponseErrorHandler responseErrorHandler){
        restTemplate.setErrorHandler(responseErrorHandler);
        return this;
    }

    public ResponseEntity<NaverBooks> exchange(){
        URI uri = uriComponentsBuilder.encode().build().toUri();
        return restTemplate.exchange(uri, getHttpMethod(), requestEntity, NaverBooks.class);
    }

    public HttpMethod getHttpMethod(){
        return HttpMethod.resolve(method);
    }

    public HttpRequest getHttpRequest() {
        NaverBookRestTemplate restTemplate = this;
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
