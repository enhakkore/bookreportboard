package com.practice.bookreportboard.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileHandlerUnitTest {
    MockEnvironment environment;
    ProfileHandler profileHandler;

    @BeforeEach
    void setUp() {
        environment = new MockEnvironment();
        profileHandler = new ProfileHandler(environment);
    }

    @Test
    void defaultProfileTest() {
        String profile = profileHandler.getCurrentEnvProfile();
        assertEquals("default", profile);
    }

    @Test
    void realProfileTest() {
        environment.setActiveProfiles("anyProfile");
        String profile = profileHandler.getCurrentEnvProfile();
        assertEquals("anyProfile", profile);
    }

    @Test
    void real1ProfileTest() {
        environment.setActiveProfiles("real1");
        String profile = profileHandler.getCurrentEnvProfile();
        assertEquals("real1", profile);
    }

    @Test
    void real2ProfileTest() {
        environment.setActiveProfiles("real2");
        String profile = profileHandler.getCurrentEnvProfile();
        assertEquals("real2", profile);
    }
}
