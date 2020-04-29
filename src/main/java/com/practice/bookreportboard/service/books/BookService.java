package com.practice.bookreportboard.service.books;

import com.practice.bookreportboard.domain.books.Book;
import com.practice.bookreportboard.domain.books.naver.NaverBookRestTemplate;
import com.practice.bookreportboard.domain.books.kakao.KakaoBookRestTemplate;
import com.practice.bookreportboard.domain.books.kakao.KakaoBooks;
import com.practice.bookreportboard.domain.books.naver.NaverBooks;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookService {

    private final NaverBookRestTemplate naverBookRestTemplate;
    private final KakaoBookRestTemplate kakaoBookRestTemplate;

    public List<Book> search(String bookTitle){
        // naver api 오류 응답에 대한 exception 처리를 하지 못함.
        ResponseEntity<NaverBooks> naverResponse = naverBookSearch(bookTitle);
        List<Book> results = naverResponse.getBody().getItems().stream().map(item -> new Book(item)).collect(Collectors.toList());

        ResponseEntity<KakaoBooks> kakaoResponse = kakaoBookSearch(bookTitle);
        return kakaoResponse.getBody().getDocuments().stream().map(doc -> new Book(doc)).collect(Collectors.toList());
    }
    public ResponseEntity<NaverBooks> naverBookSearch(String bookTitle){
        return naverBookRestTemplate.search(bookTitle);
    }
    public ResponseEntity<KakaoBooks> kakaoBookSearch(String bookTitle){
        return kakaoBookRestTemplate.search(bookTitle);
    }
}
