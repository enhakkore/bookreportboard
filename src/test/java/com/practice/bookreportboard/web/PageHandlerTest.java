package com.practice.bookreportboard.web;

import com.practice.bookreportboard.domain.posts.Posts;
import com.practice.bookreportboard.domain.posts.PostsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PageHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostsRepository postsRepository;

    private final String TEXT_HTML_UTF_8 = "text/html;charset=UTF-8";

    @Test
    void mainPageHandleTest() {
        webTestClient.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_HTML_UTF_8)
                .expectBody().consumeWith(result -> assertThat(result.getResponseBody()).contains("독후감".getBytes()));
    }

    @Test
    void postSavePageHandleTest() {
        webTestClient.get().uri("/posts/save").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_HTML_UTF_8)
                .expectBody().consumeWith(result -> assertThat(result.getResponseBody()).contains("게시글 등록".getBytes()));
    }

    @Test
    void postsUpdatePageHandleTest() {
        Posts post = Posts.builder().title("테스트").author("테스트").content("테스트")
                        .bookAuthor("테스트").bookImage("테스트").bookPublisher("테스트").bookTitle("테스트")
                        .build();

        Long id = postsRepository.save(post).getId();

        webTestClient.get().uri("/posts/update/"+id).exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_HTML_UTF_8)
                .expectBody().consumeWith(result -> assertThat(result.getResponseBody()).contains("게시글 수정".getBytes()));

        postsRepository.deleteAll();
    }

    @Test
    void searchPageHandleTest() {
        webTestClient.get().uri("/search").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_HTML_UTF_8)
                .expectBody().consumeWith(result -> assertThat(result.getResponseBody()).contains("책 검색".getBytes()));
    }

    @Test
    void bookSearchResultsPageHandleTest() {
        webTestClient.get().uri("/search/results/java").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(TEXT_HTML_UTF_8)
                .expectBody().consumeWith(result -> assertThat(result.getResponseBody()).contains("검색 결과".getBytes()));
    }
}
