package com.practice.bookreportboard.service.books;

import com.practice.bookreportboard.domain.books.NaverBookRestTemplate;
import com.practice.bookreportboard.domain.books.NaverBooks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final NaverBookRestTemplate naverBookRestTemplate;

    public NaverBooks search(String bookTitle){
        return naverBookRestTemplate.search(bookTitle).getBody();
    }
}
