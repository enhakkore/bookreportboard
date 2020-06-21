# 독후감 게시판 [![Build Status](https://travis-ci.org/enhakkore/bookreportboard.svg?branch=master)](https://travis-ci.org/enhakkore/bookreportboard)  
* 독후감을 쓸 책을 검색해 선택하고 게시글을 작성하는 웹 애플리케이션  
---

### 사용 기술  
* Back-end  
    * OpenJDK 11.0.7
    * Spring Boot 2.2.5
    * WebFlux    
    * JPA - Hibernate  
    * RDBMS - H2 database  
    * JUnit5  
* Front-end  
    * Template - Mustache  
    * Library - jQuery, Bootstrap  
* DevOps  
    * AWS EC2, S3, CodeDeploy  
    * Travis CI  

### 기능  
* 책 검색  
* 게시판(등록,수정,삭제)  

### 정보  
* API 통신에 필요한 값이 있는 `application-api.properties`는 git에서 제외하였습니다.  
    ```
    naver.id=****
    naver.secret=****
    naver.host=openapi.naver.com
    naver.path=/v1/search/book_adv.json

    kakao.secret=KakaoAK ****
    kakao.host=dapi.kakao.com
    kakao.path=/v3/search/book
    ```

### 특징  
#### WebClient에 filter를 추가하여 에러 처리  
```java  
@Component
public class KakaoBookWebClient {
    ...

    public static <R extends Mono<ClientResponse>> ExchangeFilterFunction createResponseFilter(Function<ClientResponse, R> function){
        return ExchangeFilterFunction.ofResponseProcessor( clientResponse -> {
            if(clientResponse.rawStatusCode() >= 400){
                return function.apply(clientResponse);
            }
            return Mono.just(clientResponse);
        });
    }

    ...
}
```  
* ExchangeFilterFunction은 WebClient가 보낸는 요청과 응답을 처리할 수 있는 필터입니다.  

* ExchangeFilterFunction은 기능을 1개 가지고 있는 Functional Interface 입니다. 가지고 있는 기능 1개는 ClientRequest와 ExchangeFunction을 받아 Mono\<ClientResponse\>를 반환하는 filter라는 메서드 입니다. Functional Interface는 default 메서드 또는 static 메서드 몸통에 로직을 넣어 완성된 기능을 추가할 수 있습니다.  
이렇게 추가된 기능 중 하나가 ofResponseProcessor 메서드 입니다. 이 메서드는 ClientResponse를 받아 Mono\<ClientResponse\>를 반환하는 함수를 입력으로 받고 ExchangeFunction을 반환합니다.  
ofResponseProcessor 메서드는 Function\<T, R\> Functional Interface으로 매개변수화된 함수를 입력받습니다. ofResponseProcessor는 매개변수로 받은 함수를 filter 메서드의 반환값을 처리하는 로직을 람다식을 사용해 ExchangeFunction을 만들어 반환합니다.  
저는 여기서 ClientResponse가 갖고 있는 rawStatusCode가 400 이상이면 수행하는 로직을 Function\<ClientResponse, Mono\<ClientResponse\>\>로 매개변수화 된 함수를 입력으로 받아 ExchangeFilterFunction을 생성할 수 있게 커스텀 WebClient(여기서는 KakaoBookWebClient)에 정적 메서드로 추가했습니다.  


```java  
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
```
* KakaoBookWebClient에 있는 정적 메서드를 이용해 ExchangeFunction을 만들어 WebClient에 추가하고 build 합니다. 만든 ExchangeFunction의 로직은 카카오 책 검색 API 응답 코드가 400 이상이면 네이버 책 검색 API에 요청을 보내서 받은 응답(ClientResponse)을 반환하는 것입니다. 네이버 책 검색 API 응답 코드도 400 이상이면 스프링에 기본으로 설정된 에러 처리를 따르도록 했습니다.  

* WebClient에는 retrive 메서드와 exchange 메서드가 있습니다. retrive 메서드를 사용하면 WebClient가 보낸 요청과 응답에서 발생하는 모든 과정(성공, 실패)을 스프링에 설정된 처리 방식에 따라 처리됩니다. exchange 메서드를 사용하면 retrive 메서드를 사용할 때와는 반대로 모든 과정을 exchange 메서드 이후에 설정한 로직을 따라 처리하게 됩니다. 따라서 특별한 처리 과정을 적용할 일이 없다면 retrive를 사용하는게 편리합니다.  
여기서는 카카오 책 검색 API를 담당하는 WebClient는 retrive를, 네이버 책 검색 API를 사용하는 WebClient는 exchange를 사용했습니다. 그리고 카카오 책 검색 API를 담당하는 WebClient에 에러 발생시 네이버 책 검색 API를 이용하는 ExchangeFunction을 추가하였습니다.  
즉, 이 애플리케이션은 카카오 책 검색 API를 먼저 이용하고 에러 발생시 네이버 책 검색 API를 이용하도록 되어있습니다. 따라서 카카오 API를 담당하는 WebClient의 최종 응답은 카카오 API 응답이거나 네이버 API 응답일 수 있습니다. 전체적인 API 통신 과정은 카카오 API를 담당하고 retrive를 사용한 WebClient이기 때문에 네이버 API까지 에러가 발생하면 스프링에 설정된 에러 처리 과정을 따르게 됩니다.  
