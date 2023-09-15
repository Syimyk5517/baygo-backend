package com.example.baygo.api.paypal;


import com.example.baygo.db.dto.request.paypal.CreatePaymentRequest;
import com.example.baygo.db.dto.response.paypal.CreatePaymentResponse;
import com.example.baygo.service.StripeServices;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/stripe")
@RequiredArgsConstructor
@Tag(name = "Payment Stripe API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StripeApi {
    private final StripeServices stripeServices;
    @Value("${stripe.public.key}")
    private String stripePublicKey;
    @PostMapping("/create-payment-intent")
    @PermitAll
    @Operation(summary = "Creating a payment",
            description = "This method creates payment with stripe system.")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePaymentRequest request) throws StripeException {
        return stripeServices.createPaymentIntent(request);
    }

    @GetMapping("/public_key")
    @PermitAll
    public String key(){
        return stripePublicKey;
    }

}
