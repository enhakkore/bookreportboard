package com.practice.bookreportboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookreportboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookreportboardApplication.class, args);
    }

}
