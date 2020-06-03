package com.practice.bookreportboard.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.bookreportboard.domain.posts.Posts;
import com.practice.bookreportboard.domain.posts.PostsRepository;
import com.practice.bookreportboard.web.dto.PostsSaveRequestDto;
import com.practice.bookreportboard.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiHandlerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PostsRepository postsRepository;

    private final String base_path = "/api/v1/posts/";

    @AfterEach
    void repoDeleteAll(){
        postsRepository.deleteAll();
    }

    @Test
    void postsSaveTest() {
        PostsSaveRequestDto dto = PostsSaveRequestDto.builder().title("테스트").author("테스트").content("테스트")
                                                .bookTitle("테스트").bookAuthor("테스트").bookImage("테스트").bookPublisher("테스트")
                                                .build();

        webTestClient.post().uri(base_path)
                        .body(Mono.just(dto), PostsSaveRequestDto.class)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody().consumeWith(result -> {
                            Posts entity = postsRepository.findAll().get(0);

                            assertThat(result.getResponseBody()).isNotNull();

                            String body = new String(result.getResponseBody());
                            Long idFromBody = Long.valueOf(body);

                            assertThat(entity.getId()).isEqualTo(idFromBody);
                            assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
                            assertThat(entity.getAuthor()).isEqualTo(dto.getAuthor());
                            assertThat(entity.getContent()).isEqualTo(dto.getContent());
                            assertThat(entity.getBookTitle()).isEqualTo(dto.getBookTitle());
                            assertThat(entity.getBookAuthor()).isEqualTo(dto.getAuthor());
                            assertThat(entity.getBookImage()).isEqualTo(dto.getBookImage());
                            assertThat(entity.getBookPublisher()).isEqualTo(dto.getBookPublisher());
                        });
    }

    @Test
    void postsUpdateTest() {
        savePostsToRepo();
        Long id = postsRepository.findAll().get(0).getId();

        PostsUpdateRequestDto dto = PostsUpdateRequestDto.builder().title("수정_테스트").content("수정_테스트").build();

        webTestClient.patch().uri(base_path+id)
                        .body(Mono.just(dto), PostsUpdateRequestDto.class)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody().consumeWith(result -> {
                            assertThat(result.getResponseBody()).isNotNull();

                            String body = new String(result.getResponseBody());
                            Long idFromBody = Long.valueOf(body);

                            assertThat(id).isEqualTo(idFromBody);
        });

        Posts entity = postsRepository.findAll().get(0);
        assertThat(entity.getTitle()).isEqualTo(dto.getTitle());
        assertThat(entity.getContent()).isEqualTo(dto.getContent());
    }

    @Test
    void postsGetTest() {
        Posts entity = savePostsToRepo();
        Long id = postsRepository.findAll().get(0).getId();

        ObjectMapper objectMapper = new ObjectMapper();

        webTestClient.get().uri(base_path+id)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody(). consumeWith(result -> {
                            assertThat(result.getResponseBody()).isNotNull();

                            String body = new String(result.getResponseBody());
                            try {
                                Posts value = objectMapper.readValue(body, Posts.class);
                                assertThat(value.getTitle()).isEqualTo(entity.getTitle());
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
        });
    }

    @Test
    void postsDeleteTest(){
        Posts entity = savePostsToRepo();
        Long id = entity.getId();

        webTestClient.delete().uri(base_path+id)
                        .exchange()
                        .expectStatus().isOk()
                        .expectBody().consumeWith(result -> {
                            assertThat(result.getResponseBody()).isNotNull();

                            String body = new String(result.getResponseBody());
                            Long idFromBody = Long.valueOf(body);

                            assertThat(idFromBody).isEqualTo(id);
                            assertThat(postsRepository.findAllDesc().size()).isEqualTo(0);
        });
    }

    Posts savePostsToRepo() {
        Posts entity = Posts.builder().title("테스트").author("테스트").content("테스트")
                .bookTitle("테스트").bookAuthor("테스트").bookImage("테스트").bookPublisher("테스트")
                .build();

        postsRepository.save(entity);

        return entity;
    }

}
