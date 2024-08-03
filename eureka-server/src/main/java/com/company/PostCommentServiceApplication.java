package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class PostCommentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PostCommentServiceApplication.class, args);
    }

}
