package com.example.baygo.db.api;

import com.example.baygo.db.service.SupplierService;
import com.example.baygo.dto.response.TransitDirectionResponse;
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
public class TransitDirectionApi {
    private final SupplierService service;
    @Operation(summary = "Transit direction from warehouse",description = "This method transit direction of product of warehouse.")
    @GetMapping
    public List<TransitDirectionResponse> getAllTransactions(String location){
        return service.getAllTransactions(location);
    }

}
