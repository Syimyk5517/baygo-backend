package com.example.baygo.service;

import com.example.baygo.db.dto.request.AuthenticateRequest;
import com.example.baygo.db.dto.request.BuyerRegisterRequest;
import com.example.baygo.db.dto.request.SellerRegisterRequest;
import com.example.baygo.db.dto.response.AuthenticationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse buyerRegister(BuyerRegisterRequest request);

    AuthenticationResponse sellerRegister(SellerRegisterRequest request);

    AuthenticationResponse authenticate(AuthenticateRequest request);

    SimpleResponse forgotPassword(String email);

    SimpleResponse resetPassword(String token, String newPassword);

    AuthenticationResponse authWithGoogle(String tokenId) throws FirebaseAuthException;
}
