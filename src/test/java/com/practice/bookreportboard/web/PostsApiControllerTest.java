package com.practice.bookreportboard.web;

import com.practice.bookreportboard.domain.posts.Posts;
import com.practice.bookreportboard.domain.posts.PostsRepository;
import com.practice.bookreportboard.web.dto.PostsSaveRequestDto;
import com.practice.bookreportboard.web.dto.PostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @AfterEach
    public void tearDown() throws Exception {
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_등록된다() {
        String title = "제목";
        String author = "저자";
        String content = "테스트내용";
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder().title(title).author(author).content(content).build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void Posts_수정된다() {
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder().title("제목").author("작성자").content("내용").build();
        String url = "http://localhost:"+port+"/api/v1/posts";
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, postsSaveRequestDto, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        long expectedId = postsRepository.findAll().get(0).getId();
        String expectedTitle = "바꾼제목";
        String expectedContent = "바꾼내용";

        PostsUpdateRequestDto postsUpdateRequestDto = PostsUpdateRequestDto.builder().title(expectedTitle).content(expectedContent).build();
        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(postsUpdateRequestDto);
        url += "/" + expectedId;
        ResponseEntity<Long> responseEntity1 = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        assertThat(responseEntity1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity1.getBody()).isGreaterThan(0L);

        Posts updatedPost = postsRepository.findAll().get(0);
        assertThat(updatedPost.getTitle()).isEqualTo(expectedTitle);
        assertThat(updatedPost.getContent()).isEqualTo(expectedContent);
    }

}