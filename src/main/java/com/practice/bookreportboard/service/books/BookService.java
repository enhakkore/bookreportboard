package com.practice.bookreportboard.service.books;

import com.practice.bookreportboard.domain.books.naver.NaverBookRestTemplate;
import com.practice.bookreportboard.domain.books.kakao.KakaoBookRestTemplate;
import com.practice.bookreportboard.domain.books.kakao.KakaoBooks;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookService {

    private final NaverBookRestTemplate naverBookRestTemplate;
    private final KakaoBookRestTemplate kakaoBookRestTemplate;

    public ResponseEntity<KakaoBooks> search(String title){
        naverBookRestTemplate.setMethod("GET")
                            .setQuery(title);

        return kakaoBookRestTemplate.setMethod("GET")
                            .setQuery("title", title)
                            .addInterceptor((request, body, execution) -> {
                                ClientHttpResponse response = execution.execute(request, body);
                                if(response.getRawStatusCode() >= 400) {
                                    log.error("카카오 API 응답 에러 발생, 에러 코드 : {}", response.getRawStatusCode());
                                    return execution.execute(naverBookRestTemplate.getHttpRequest(), body);
                                }
                                return response;
                            })
                            .exchange();
    }
}
