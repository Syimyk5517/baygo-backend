package com.example.baygo.api.buyer;
import com.example.baygo.db.dto.request.buyer.ReturnProductRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.ReturnGetByIdResponse;
import com.example.baygo.db.dto.response.buyer.ReturnsResponse;
import com.example.baygo.service.BuyerReturnService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
@Tag(name = "Buyer returns", description = "Returns of buyer")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAuthority('BUYER')")
public class BuyerReturnController {
    private final BuyerReturnService service;
    @Operation(summary = "Save Return", description = "This method save return of Buyer!")
    @PostMapping
    public SimpleResponse createReturn(@RequestBody @Valid ReturnProductRequest request) {
        return service.save(request);
    }
    @Operation(summary = "Get all returns",description = "this method will get all returns of buyer")
    @GetMapping
    public List<ReturnsResponse> getAllReturns(){
        return service.getAll();
    }

    @Operation(summary="Get by supplyId of return",description = "This method wil get return by supplyId")
    @GetMapping("/{returnId}")
    public ReturnGetByIdResponse getByIdReturn(@PathVariable Long returnId){
        return service.getById(returnId);
    }
}
