package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.auth.AuthenticateRequest;
import com.example.baygo.db.dto.request.BuyerRegisterRequest;
import com.example.baygo.db.dto.request.SellerRegisterRequest;
import com.example.baygo.db.dto.response.AuthenticationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface AuthenticationService {
    AuthenticationResponse buyerRegister(BuyerRegisterRequest request);

    AuthenticationResponse sellerRegister(SellerRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticateRequest request);
    SimpleResponse forgotPassword(String email);
    SimpleResponse resetPassword(String token, String newPassword);
}
