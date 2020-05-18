package com.practice.bookreportboard.domain.books.kakao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
public class KakaoBookWebClient {

    @Value("${kakao.host}")
    private String host;

    @Value("${kakao.path}")
    private String path;

    @Value("${kakao.secret}")
    private String CLIENT_SECRET;

    private WebClient client;
    private UriComponents uriComponents;
    private List<ExchangeFilterFunction> exchangeFilterFunctionList;

    public static <R extends Mono<ClientResponse>> ExchangeFilterFunction createResponseFilter(Function<ClientResponse, R> function){
        return ExchangeFilterFunction.ofResponseProcessor( clientResponse -> {
            if(clientResponse.rawStatusCode() >= 400){
                return function.apply(clientResponse);
            }
            return Mono.just(clientResponse);
        });
    }

    public KakaoBookWebClient addFilter(ExchangeFilterFunction exchangeFilterFunction){
        if(Objects.isNull(exchangeFilterFunctionList))
            this.exchangeFilterFunctionList = new LinkedList<>();
        this.exchangeFilterFunctionList.add(exchangeFilterFunction);

        return this;
    }

    public KakaoBookWebClient buildWebClinet(){
        if(Objects.isNull(client)) {
            client = WebClient.builder()
                    .filters(list -> list.addAll(exchangeFilterFunctionList))
                    .defaultHeader("Authorization", CLIENT_SECRET)
                    .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }

        return this;
    }

    public KakaoBookWebClient setQuery(String target, String query) {
        this.uriComponents = UriComponentsBuilder.newInstance()
                .scheme("https").host(host).path(path)
                .queryParam("target", target)
                .queryParam("query", query)
                .encode().build();

        return this;
    }

    public WebClient getClient(){
        return this.client;
    }

    public URI getUri(){
        return this.uriComponents.toUri();
    }
}
