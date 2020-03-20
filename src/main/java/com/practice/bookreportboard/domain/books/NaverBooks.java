package com.practice.bookreportboard.domain.books;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverBooks {
    private String lastBuildDate;
    private String total;
    private String start;
    private String display;
    private List<Book> items;
}
