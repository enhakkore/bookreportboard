package com.practice.bookreportboard.web.dto;

import com.practice.bookreportboard.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String bookTitle;
    private String bookAuthor;
    private String bookImage;
    private String bookPublisher;

    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.content = entity.getContent();
        this.bookAuthor = entity.getBookAuthor();
        this.bookImage = entity.getBookImage();
        this.bookPublisher = entity.getBookPublisher();
        this.bookTitle = entity.getBookTitle();
    }
}
