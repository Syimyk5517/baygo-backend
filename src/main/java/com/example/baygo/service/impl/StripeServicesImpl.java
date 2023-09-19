package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.paypal.CreatePaymentRequest;
import com.example.baygo.db.dto.response.paypal.CreatePaymentResponse;
import com.example.baygo.service.StripeServices;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripeServicesImpl implements StripeServices {
    @Override
    public CreatePaymentResponse createPaymentIntent(CreatePaymentRequest request) throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(request.getAmount() * 100L)
                        .setCurrency("kgs")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build())
                        .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return new CreatePaymentResponse(paymentIntent.getClientSecret());
    }
}
