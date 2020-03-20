package com.practice.bookreportboard.web.dto;

import com.practice.bookreportboard.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime modifiedDate;
    private String bookTitle;
    private String bookImage;

    public PostsListResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.modifiedDate = entity.getModifiedDate();
        this.bookImage = entity.getBookImage();
        this.bookTitle = entity.getBookTitle();
    }
}
