package com.practice.bookreportboard.config;

import com.practice.bookreportboard.web.PageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class CustomConfig {

    private final PageHandler pageHandler;

    @Bean
    public RouterFunction<ServerResponse> pageRouterFunction() {
        return RouterFunctions.route(GET("/"), pageHandler::mainPage)
                .andRoute(GET("/posts/save"), pageHandler::postsSave)
                .andRoute(GET("/posts/update/{id}"), pageHandler::postsUpdate)
                .andRoute(GET("/search"), pageHandler::search)
                .andRoute(GET("/search/results/{bookTitle}"), pageHandler::bookSearchResults);
    }
}
