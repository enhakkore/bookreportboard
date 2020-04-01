package com.practice.bookreportboard.domain.books.kakao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Book {
    private String title;
    private String content;
    private String url;
    private String isbn;
    private Date datetime;
    private List<String> authors;
    private String publisher;
    private List<String> translators;
    private Integer price;
    private Integer sale_price;
    private String thumbnail;
    private String status;
}
