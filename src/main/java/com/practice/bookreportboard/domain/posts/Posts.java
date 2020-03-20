package com.practice.bookreportboard.domain.posts;

import com.practice.bookreportboard.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    private String bookTitle;
    private String bookImage;
    private String bookPublisher;
    private String bookAuthor;

    @Builder
    public Posts(String title, String content, String author, String bookTitle, String bookAuthor, String bookImage, String bookPublisher){
        this.title = title;
        this.content = content;
        this.author = author;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
        this.bookPublisher = bookPublisher;
        this.bookTitle = bookTitle;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }
}
