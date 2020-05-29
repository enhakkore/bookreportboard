package com.practice.bookreportboard.domain.books.naver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class NaverBookWebClient {

    @Value("${naver.id}")
    private String CLIENT_ID;

    @Value("${naver.secret}")
    private String CLIENT_SECRET;

    @Value("${naver.host}")
    private String host;

    @Value("${naver.path}")
    private String path;

    private WebClient client;
    private UriComponents uriComponents;
    private List<ExchangeFilterFunction> exchangeFilterFunctionList = new LinkedList<>();

    public NaverBookWebClient addFilter(ExchangeFilterFunction exchangeFilterFunction){
        exchangeFilterFunctionList.add(exchangeFilterFunction);

        return this;
    }

    public NaverBookWebClient buildWebClient() {
        if(Objects.isNull(client)) {
            client = WebClient.builder()
                    .filters(list -> list.addAll(exchangeFilterFunctionList))
                    .defaultHeader("X-Naver-Client-Id", CLIENT_ID)
                    .defaultHeader("X-Naver-Client-Secret", CLIENT_SECRET)
                    .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }

        return this;
    }

    public NaverBookWebClient setQuery(String target, String query){
        uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host(host).path(path)
                .queryParam(target, query)
                .encode()
                .build();

        return this;
    }

    public WebClient getClient() {
        return this.client;
    }

    public URI getUri() {
        return this.uriComponents.toUri();
    }
}
