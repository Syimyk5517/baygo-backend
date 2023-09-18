package com.example.baygo.service;


import com.example.baygo.db.dto.request.paypal.CreatePaymentRequest;
import com.example.baygo.db.dto.response.paypal.CreatePaymentResponse;
import com.stripe.exception.StripeException;

public interface StripeServices {
    CreatePaymentResponse createPaymentIntent(CreatePaymentRequest request) throws StripeException;
}
