package com.practice.bookreportboard.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @AfterEach
    public void cleanUp(){
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        String title = "테스크 게시글";
        String content = "테스트 본문";
        String author = "AA@AA";

        postsRepository.save(Posts.builder().title(title).content(content).author(author).build());

        List<Posts> postsList = postsRepository.findAll();

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getAuthor()).isEqualTo(author);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        LocalDateTime now = LocalDateTime.now();
        Posts post = Posts.builder().title("제목").content("content").author("author").build();
        postsRepository.save(post);

        Posts saved = postsRepository.findAll().get(0);

        assertThat(saved.getCreateDate()).isAfter(now);
        assertThat(saved.getModifiedDate()).isAfter(now);

    }
}