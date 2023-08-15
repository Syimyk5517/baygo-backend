package com.example.baygo.api.fbs;

import com.example.baygo.db.dto.request.fbs.SupplyRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.SupplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fbs/supply")
@RequiredArgsConstructor
@Tag(name = "Fbs Supply ")
@CrossOrigin(value = "*",maxAge = 3600)
@PreAuthorize("hasAnyAuthority('SELLER')")
public class FbsSupplyController {
    private final SupplyService supplyService;
    @PostMapping
    public SimpleResponse saveSupply(@RequestBody SupplyRequest supplyRequest){
     return supplyService.saveSupply(supplyRequest);
    }

}
