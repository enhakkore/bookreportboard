package com.practice.bookreportboard.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfileHandlerTest {

    @Autowired
    WebTestClient client;

    @Test
    void profileTest() {
        client.get().uri("/profile").exchange()
                .expectStatus().isOk()
                .expectBody().consumeWith( result -> {
                    assertNotNull(result.getResponseBody());

                    String body = new String(result.getResponseBody());
                    assertEquals("default", body);
                });
    }
}
