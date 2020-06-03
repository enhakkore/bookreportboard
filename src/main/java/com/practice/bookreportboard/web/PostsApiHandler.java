package com.practice.bookreportboard.web;

import com.practice.bookreportboard.service.posts.PostsService;
import com.practice.bookreportboard.web.dto.PostsResponseDto;
import com.practice.bookreportboard.web.dto.PostsSaveRequestDto;
import com.practice.bookreportboard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PostsApiHandler {

    private final PostsService postsService;

    public Mono<ServerResponse> save(ServerRequest request){
        Mono<Long> save = request.bodyToMono(PostsSaveRequestDto.class).map(postsService::save);
        return ServerResponse.ok().body(save, Long.class);
    }

    public Mono<ServerResponse> update(ServerRequest request){
        final Long id = getId(request);
        Mono<Long> update = request.bodyToMono(PostsUpdateRequestDto.class).map(dto -> postsService.update(id, dto));
        return ServerResponse.ok().body(update, Long.class);
    }

    public Mono<ServerResponse> get(ServerRequest request){
        Long id = getId(request);
        return ServerResponse.ok().body(Mono.just(postsService.get(id)), PostsResponseDto.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        Long id = getId(request);
        postsService.delete(id);
        return ServerResponse.ok().body(Mono.just(id), Long.class);
    }

    private Long getId(ServerRequest request){
        String pathVal = Optional.of(request.pathVariable("id")).get();
        return Long.parseLong(pathVal);
    }

}
