package com.practice.bookreportboard.service.books;

import com.practice.bookreportboard.domain.books.kakao.KakaoBookWebClient;
import com.practice.bookreportboard.domain.books.naver.NaverBookWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final KakaoBookWebClient kakaoBookWebClient;
    private final NaverBookWebClient naverBookWebClient;

    public WebClient.ResponseSpec kakaoBookSearch(String title){
        ExchangeFilterFunction errorHandleFilter = KakaoBookWebClient.createResponseFilter( clientResponse -> {
            log.error("카카오 책 검색 API 응답 에러 발생, 에러 코드 : {}", clientResponse.rawStatusCode());
            return naverBookSearch(title);
        });

        URI uri = kakaoBookWebClient.setQuery("title", title).getUri();
        WebClient client = kakaoBookWebClient.addFilter(errorHandleFilter).buildWebClinet().getClient();
        return client.get().uri(uri).retrieve();
    }

    public Mono<ClientResponse> naverBookSearch(String title){
        ExchangeFilterFunction errorHandleFilter = KakaoBookWebClient.createResponseFilter(clientResponse -> {
            log.error("네이버 책 검색 API 응답 에러 발생, 에러 코드 : {}", clientResponse.rawStatusCode());
            return Mono.just(clientResponse);
        });

        URI uri = naverBookWebClient.setQuery("d_titl", title).getUri();
        WebClient client = naverBookWebClient.addFilter(errorHandleFilter).buildWebClient().getClient();
        return client.get().uri(uri).exchange();
    }
}
