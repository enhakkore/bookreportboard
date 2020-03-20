package com.practice.bookreportboard.domain.books;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
    private String title;
    private String link;
    private String image;
    private String author;
    private String price;
    private String discount;
    private String publisher;
    private String isbn;
    private String description;
    private String pubdate;
}
