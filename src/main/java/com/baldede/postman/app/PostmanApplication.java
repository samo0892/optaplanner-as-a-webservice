package com.baldede.postman.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.baldede.postman.*")
@EntityScan("com.baldede.postman.domain")
public class PostmanApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostmanApplication.class, args);
    }

}
