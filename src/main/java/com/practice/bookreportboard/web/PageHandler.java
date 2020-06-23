package com.practice.bookreportboard.web;

import com.practice.bookreportboard.domain.books.Book;
import com.practice.bookreportboard.domain.books.kakao.KakaoBooks;
import com.practice.bookreportboard.service.books.BookService;
import com.practice.bookreportboard.service.posts.PostsService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class PageHandler {
    private final BookService bookService;
    private final PostsService postsService;

    public Mono<ServerResponse> mainPage(ServerRequest serverRequest) {
        return ServerResponse.ok().render("index", Map.of("posts", postsService.findAllDesc()));
    }

    public Mono<ServerResponse> postsSave(ServerRequest serverRequest){
        return ServerResponse.ok().render("posts-save");
    }

    public Mono<ServerResponse> postsUpdate(ServerRequest serverRequest){
        Long id = Long.valueOf(serverRequest.pathVariable("id"));
        return ServerResponse.ok().render("posts-update", Map.of("post", postsService.get(id)));
    }

    public Mono<ServerResponse> search(ServerRequest serverRequest){
        return ServerResponse.ok().render("search");
    }

    public Mono<ServerResponse> bookSearchResults(ServerRequest serverRequest){
        String bookTitle = Optional.of(serverRequest.pathVariable("bookTitle")).get();

        Mono<List<Book>> results = bookService.kakaoBookSearch(bookTitle).bodyToMono(KakaoBooks.class)
                .map(kakaoBooks -> {
                    if(Objects.isNull(kakaoBooks.getDocuments()))
                        return kakaoBooks.getItems().stream().map(Book::new).collect(toList());
                    else return kakaoBooks.getDocuments().stream().map(Book::new).collect(toList());
                });

        return ServerResponse.ok().render("searchResults", Map.of("books", results));
    }
}