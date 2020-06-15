package com.practice.bookreportboard.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ProfileHandler {

    private final Environment environment;

    public Mono<ServerResponse> profile(ServerRequest serverRequest) {
        String bodyValue = getCurrentEnvProfile();
        return ServerResponse.ok().body(BodyInserters.fromValue(bodyValue));
    }

    public String getCurrentEnvProfile() {
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }

}
