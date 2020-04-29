package com.practice.bookreportboard.domain.books;

import com.practice.bookreportboard.domain.books.kakao.Document;
import com.practice.bookreportboard.domain.books.kakao.KakaoBooks;
import com.practice.bookreportboard.domain.books.naver.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    public Book(Document doc){
        this.title = doc.getTitle();
        this.link = doc.getUrl();
        this.image = doc.getThumbnail();
        this.author = doc.getAuthors().stream().collect(Collectors.joining(", "));
        this.price = doc.getPrice();
        this.discount = doc.getSale_price();
        this.publisher = doc.getPublisher();
        this.isbn = doc.getIsbn();
        this.description = doc.getContent();
        this.pubdate = doc.getDatetime();
    }

    public Book(Item item){
        this.title = item.getTitle();
        this.link = item.getLink();
        this.image = item.getImage();
        this.author = item.getAuthor();
        this.price = item.getPrice();
        this.discount = item.getDiscount();
        this.publisher = item.getPublisher();
        this.isbn = item.getIsbn();
        this.description = item.getDescription();
        this.pubdate = item.getPubdate();
    }
}
