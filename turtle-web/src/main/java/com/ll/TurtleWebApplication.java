package com.ll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class TurtleWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(TurtleWebApplication.class, args);
    }
}
