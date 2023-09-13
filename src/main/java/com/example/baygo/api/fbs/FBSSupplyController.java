package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.service.FBSSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fbs/supply")
@RequiredArgsConstructor
@Tag(name = "Fbs Supply ")
@CrossOrigin(value = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class FBSSupplyController {
    private final FBSSupplyService supplyService;

    @Operation(summary = "Get all supply of FBS seller", description = "This method will get all supplies")
    @GetMapping("/supplies")
    public List<GetAllFbsSupplies> getAllFbsSupplies() {
        return supplyService.getAllFbsSupplies();
    }

    @Operation(summary = "Supply get by supplyId", description = "This method will get supply by supplyId")
    @GetMapping("/{supplyId}")
    public GetSupplyWithOrders getByIdFbsSupply(@PathVariable Long supplyId) {
        return supplyService.getSupplyByIdWithOrders(supplyId);
    }

    @Operation(summary = "Add orders to supply", description = "This method will add new orders to fbs supply")
    @PostMapping("/add-orders-to-supply")
    public SimpleResponse saveAssemblyTask(@RequestBody @Valid SupplyOrderRequest supplyOrderRequest) {
        return supplyService.saveAssemblyTask(supplyOrderRequest);
    }

    @Operation(summary = "Create supply", description = "This method will create the supply")
    @PostMapping
    public SimpleResponse createSupply(@RequestParam Long warehouseId, @RequestParam String nameOfSupply) {
        return supplyService.createSupply(warehouseId, nameOfSupply);
    }
}