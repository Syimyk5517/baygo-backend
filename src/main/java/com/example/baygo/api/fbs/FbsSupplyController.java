package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.SupplyOrderRequest;
import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.GetAllFbsSupplies;
import com.example.baygo.db.dto.response.fbs.GetSupplyWithOrders;
import com.example.baygo.service.FBSSupplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fbs/supply")
@RequiredArgsConstructor
@Tag(name = "Fbs Supply ")
@CrossOrigin(value = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class FbsSupplyController {
    private final FBSSupplyService supplyService;

    @PostMapping("/add_quantity")
    public SimpleResponse saveSupply(@RequestBody @Valid SupplyRequest supplyRequest) {
        return supplyService.saveSupply(supplyRequest);
    }

    @GetMapping("/supplies")
    public List<GetAllFbsSupplies> getAllFbsSupplies() {
        return supplyService.getAllFbsSupplies();
    }

    @GetMapping("/{supplyId}")
    public GetSupplyWithOrders getByIdFbsSupply(@PathVariable Long supplyId) {
        return supplyService.getSupplyByIdWithOrders(supplyId);
    }

    @PostMapping("/save/assembly")
    public SimpleResponse saveAssemblyTask(@RequestBody @Valid SupplyOrderRequest supplyOrderRequest) {
        return supplyService.saveAssemblyTask(supplyOrderRequest);
    }
}