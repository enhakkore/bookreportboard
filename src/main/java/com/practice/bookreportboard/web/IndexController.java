package com.practice.bookreportboard.web;

import com.practice.bookreportboard.domain.books.Book;
import com.practice.bookreportboard.domain.books.kakao.KakaoBooks;
import com.practice.bookreportboard.service.books.BookService;
import com.practice.bookreportboard.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final BookService bookService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(Model model, @PathVariable Long id) {
        model.addAttribute("post", postsService.get(id));
        return "posts-update";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/search/results/{bookTitle}")
    public Rendering bookSearchResults(@PathVariable String bookTitle){
        Mono<List<Book>> results = bookService.kakaoBookSearch(bookTitle)
                                    .flatMap(clientResponse -> clientResponse.bodyToMono(KakaoBooks.class))
                                    .map(kakaoBooks -> {
                                        if(Objects.nonNull(kakaoBooks.getErrorContent()))
                                            return Collections.singletonList(new Book(kakaoBooks.getErrorContent()));

                                        if(Objects.isNull(kakaoBooks.getDocuments()))
                                            return kakaoBooks.getItems().stream().map(Book::new).collect(toList());
                                        else return kakaoBooks.getDocuments().stream().map(Book::new).collect(toList());
                                    });

        return Rendering.view("searchResults")
                .modelAttribute("books", results)
                .build();
    }
}
