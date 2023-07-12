package com.example.baygo.db.api;

import com.example.baygo.db.service.SupplyTransitDirectionService;
import com.example.baygo.dto.response.SupplyTransitDirectionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supply")
@RequiredArgsConstructor
@Tag(name = "Supply API")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplyApi {
    private final SupplyTransitDirectionService service;
    @Operation(summary = "Transit direction from warehouse.",description = "This method transit direction of product of warehouse.")
    @GetMapping
    public List<SupplyTransitDirectionResponse> getAllTransactions(String location){
        return service.getAllTransactions(location);
    }

}
