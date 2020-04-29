package com.practice.bookreportboard.domain.books.naver;

import com.practice.bookreportboard.domain.books.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NaverBooks {
    private Date lastBuildDate;
    private Integer total;
    private Integer start;
    private Integer display;
    private List<Item> items;
}
