package com.example.baygo.service;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PayPalService {
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
     SimpleResponse successPay(String paymentId, String payerId);
}
