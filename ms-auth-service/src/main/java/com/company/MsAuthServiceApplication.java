package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Random;

@SpringBootApplication
@EnableDiscoveryClient
public class MsAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsAuthServiceApplication.class, args);
    }

    @Bean
    public Random random() {
        return new Random();
    }
}
