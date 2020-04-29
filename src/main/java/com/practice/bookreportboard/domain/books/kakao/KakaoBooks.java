package com.practice.bookreportboard.domain.books.kakao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class KakaoBooks {
    private Meta meta;
    private List<Document> documents;
}
