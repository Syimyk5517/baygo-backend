package com.example.baygo;

import com.example.baygo.db.dto.response.AuthenticationResponse;
import com.example.baygo.db.model.enums.Role;
import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableScheduling
@RestController
public class BaygoApplication {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void setup() {
        Stripe.apiKey = stripeApiKey;
    }

    public static void main(String[] args) {
        SpringApplication.run(BaygoApplication.class, args);
    }

    @GetMapping("/")
    public String greeting() {
        return "<h1> Welcome to BuyGo! <h1>";
    }

    @GetMapping("/12")
    public AuthenticationResponse get() {
        return new AuthenticationResponse("test", Role.ADMIN, "token", "message" );
    }

}
