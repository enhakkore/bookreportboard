package com.practice.bookreportboard.domain.books;

import com.practice.bookreportboard.domain.books.kakao.Document;
import com.practice.bookreportboard.domain.books.naver.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private String errorContent;

    public Book(String errorContent){
        this.errorContent = errorContent;
        this.image = "";
        this.title = "";
        this.author = "";
        this.publisher = "";
    }

    public Book(Document doc){
        this.title = doc.getTitle();
        this.link = doc.getUrl();
        this.image = doc.getThumbnail();
        this.author = String.join(", ", doc.getAuthors());
        this.price = doc.getPrice();
        this.discount = doc.getSale_price();
        this.publisher = doc.getPublisher();
        this.isbn = doc.getIsbn();
        this.description = doc.getContents();
        this.pubdate = doc.getDatetime();
        this.errorContent = "";
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
        this.errorContent = "";
    }
}
