package com.practice.bookreportboard.config;

import com.practice.bookreportboard.web.PageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class CustomConfig {

    private final PageHandler pageHandler;

    @Bean
    public RouterFunction<ServerResponse> pageRouterFunction() {
        return RouterFunctions.route(RequestPredicates.GET("/"), pageHandler::mainPage)
                .andRoute(RequestPredicates.GET("/posts/save"), pageHandler::postsSave)
                .andRoute(RequestPredicates.GET("/posts/update/{id}"), pageHandler::postsUpdate)
                .andRoute(RequestPredicates.GET("/search"), pageHandler::search)
                .andRoute(RequestPredicates.GET("/search/results/{bookTitle}"), pageHandler::bookSearchResults);
    }
}
