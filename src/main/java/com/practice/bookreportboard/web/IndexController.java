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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public String searchResults(Model model, @PathVariable String bookTitle){
        KakaoBooks responseBody = bookService.search(bookTitle).getBody();
        List<Book> books;

        if(!Objects.isNull(responseBody.getDocuments()))
            books = responseBody.getDocuments().stream().map(Book::new).collect(Collectors.toList());
        else books = responseBody.getItems().stream().map(Book::new).collect(Collectors.toList());

        model.addAttribute("books", books);
        return "searchResults";
    }
}
