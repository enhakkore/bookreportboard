package com.practice.bookreportboard.config;

import com.practice.bookreportboard.web.PageHandler;
import com.practice.bookreportboard.web.PostsApiHandler;
import com.practice.bookreportboard.web.ProfileHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

import lombok.RequiredArgsConstructor;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@RequiredArgsConstructor
@Configuration
public class CustomConfig {

    private final PageHandler pageHandler;
    private final PostsApiHandler postsApiHandler;
    private final ProfileHandler profileHandler;

    @Bean
    public RouterFunction<ServerResponse> pageRouterFunction() {
        return RouterFunctions.route(GET("/"), pageHandler::mainPage)
                .andRoute(GET("/posts/save"), pageHandler::postsSave)
                .andRoute(GET("/posts/update/{id}"), pageHandler::postsUpdate)
                .andRoute(GET("/search"), pageHandler::search)
                .andRoute(GET("/search/results/{bookTitle}"), pageHandler::bookSearchResults);
    }

    @Bean
    public RouterFunction<ServerResponse> postsApiRouterFunction() {
        return RouterFunctions.nest(path("/api/v1/posts"),
                RouterFunctions.route(POST(""), postsApiHandler::save)
                    .andNest(path("/{id}"),
                                RouterFunctions.route(PATCH(""), postsApiHandler::update)
                                    .andRoute(GET(""), postsApiHandler::get)
                                    .andRoute(DELETE(""), postsApiHandler::delete)));
    }

    @Bean
    public RouterFunction<ServerResponse> profileRouterFunction() {
        return RouterFunctions.route(GET("/profile"), profileHandler::profile);
    }
}
