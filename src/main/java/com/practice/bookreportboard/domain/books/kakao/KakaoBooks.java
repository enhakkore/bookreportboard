package com.practice.bookreportboard.domain.books.kakao;

import com.practice.bookreportboard.domain.books.naver.NaverBooks;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoBooks extends NaverBooks {
    private Meta meta;
    private List<Document> documents;
}
