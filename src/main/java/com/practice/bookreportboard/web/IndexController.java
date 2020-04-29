package com.practice.bookreportboard.web;

import com.practice.bookreportboard.service.books.BookService;
import com.practice.bookreportboard.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        model.addAttribute("books", bookService.search(bookTitle));
        return "searchResults";
    }
}
