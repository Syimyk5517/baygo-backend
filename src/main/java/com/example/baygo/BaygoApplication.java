package com.example.baygo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class BaygoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaygoApplication.class, args);
    }

    @GetMapping("/")
    public String greeting(){
        return "<h1> Welcome to BuyGo! <h1>";
    }

}
