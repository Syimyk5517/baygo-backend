package com.example.baygo.db.api;


import com.example.baygo.db.dto.request.AuthenticateRequest;
import com.example.baygo.db.dto.request.BuyerRegisterRequest;
import com.example.baygo.db.dto.request.SellerRegisterRequest;
import com.example.baygo.db.dto.response.AuthenticationResponse;
import com.example.baygo.db.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationApi {
    private final AuthenticationService authenticationService;

    @Operation(summary = "Register a new user", description = "This method validates the request and creates a new user.")
    @PostMapping("/sign-up/buyer")
    public AuthenticationResponse signUpBuyer(@RequestBody @Valid BuyerRegisterRequest request) {
        return authenticationService.buyerRegister(request);
    }

    @Operation(summary = "Register a new seller", description = "This method validates the request and creates a new seller.")
    @PostMapping("/sign-up/seller")
    public AuthenticationResponse signUpSeller(@RequestBody @Valid SellerRegisterRequest request) {
        return authenticationService.sellerRegister(request);
    }

    @Operation(summary = "Authenticate a user", description = "This method validates the request and authenticates a user.")
    @PostMapping("/sign-in")
    public AuthenticationResponse signIn(@RequestBody @Valid AuthenticateRequest request) {
        return authenticationService.authenticate(request);
    }
}
