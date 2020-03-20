package com.practice.bookreportboard.web.dto;

import com.practice.bookreportboard.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;
    private String bookAuthor;
    private String bookImage;
    private String bookPublisher;
    private String bookTitle;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, String bookTitle, String bookAuthor, String bookImage, String bookPublisher){
        this.title = title;
        this.content = content;
        this.author = author;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.bookPublisher = bookPublisher;
        this.bookTitle = bookTitle;
    }

    public Posts toEntity() {
        return Posts.builder()
                    .title(title)
                    .author(author)
                    .content(content)
                    .bookAuthor(bookAuthor)
                    .bookImage(bookImage)
                    .bookPublisher(bookPublisher)
                    .bookTitle(bookTitle)
                    .build();
    }
}
