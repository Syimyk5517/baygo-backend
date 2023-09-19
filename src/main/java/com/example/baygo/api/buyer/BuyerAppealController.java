package com.example.baygo.api.buyer;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.AppealResponse;
import com.example.baygo.service.AppealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer/appeal")
@RequiredArgsConstructor
@Tag(name = "Buyer appeal API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BuyerAppealController {
    private final AppealService appealService;

    @Operation(summary = "Save the appeal of buyer", description = "This method saves the appeals")
    @PostMapping
    @PreAuthorize("hasAuthority('BUYER')")
    SimpleResponse saveAppeal(@RequestBody @Valid AppealResponse appealResponse){
        return appealService.saveAppeal(appealResponse);
    }
}
