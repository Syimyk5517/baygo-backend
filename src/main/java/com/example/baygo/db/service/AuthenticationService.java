package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.AuthenticateRequest;
import com.example.baygo.db.dto.request.BuyerRegisterRequest;
import com.example.baygo.db.dto.request.SellerRegisterRequest;
import com.example.baygo.db.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse buyerRegister(BuyerRegisterRequest request);

    AuthenticationResponse sellerRegister(SellerRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticateRequest request);
}
