package com.example.baygo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableScheduling
public class BaygoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaygoApplication.class, args);
    }

    @RequestMapping
    public void greeting(){
        System.out.println("Welcome to Buygo");
    }
}
