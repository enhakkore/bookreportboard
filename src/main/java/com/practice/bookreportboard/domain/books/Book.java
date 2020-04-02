package com.practice.bookreportboard.domain.books;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Book {
    private String title;
    private String link;
    private String image;
    private String author;
    private Integer price;
    private Integer discount;
    private String publisher;
    private String isbn;
    private String description;
    private Date pubdate;
}
