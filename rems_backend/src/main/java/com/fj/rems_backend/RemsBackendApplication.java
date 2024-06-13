package com.fj.rems_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RemsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemsBackendApplication.class, args);
    }

}
