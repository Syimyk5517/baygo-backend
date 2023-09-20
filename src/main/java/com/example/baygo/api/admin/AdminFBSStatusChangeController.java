package com.example.baygo.api.admin;

import com.example.baygo.db.dto.request.admin.FBSStatusChangeRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.admin.AdminFBSSuppliesResponse;
import com.example.baygo.db.model.enums.FBSSupplyStatus;
import com.example.baygo.service.AdminFBSSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/fbs/supplies")
@RequiredArgsConstructor
@Tag(name = "Admin FBS Status Change Api")
@CrossOrigin(value = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminFBSStatusChangeController {
    private final AdminFBSSupplyService adminFBSSupplyService;

    @Operation(summary = "FBS supplies multiple status change", description = "This method will change FBS status")
    @PostMapping("/status-change")
    public SimpleResponse fbsStatusChange(@RequestBody @Valid FBSStatusChangeRequest request) {
        adminFBSSupplyService.statusChange(request.getSupplyIds(), request.getNewStatus());
        return new SimpleResponse(HttpStatus.OK, String.format("FBS статус поставки изменен на  статус %s успешно", request.getNewStatus()));
    }

    @Operation(summary = "Get all fbs supply status", description = "This method will get all status of fbs supplies")
    @GetMapping("/statuses")
    public List<FBSSupplyStatus> getAllStatus() {
        return adminFBSSupplyService.getAllStatus();

    }

    @Operation(summary = "Get all fbs supplies", description = "This method will get all fb supplies")
    @GetMapping
    public PaginationResponse<AdminFBSSuppliesResponse> getAll(@RequestParam(required = false) String keyWord,
                                                               @RequestParam(defaultValue = "1") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return adminFBSSupplyService.getAllSupplies(keyWord, page, size);
    }

    @Operation(summary = "Count total FBS supply quantity")
    @GetMapping("/total-quantity")
    public long countTotalSupplyQuantity() {
        return adminFBSSupplyService.countTotalSupplyQuantity();
    }

    @Operation(summary = "Count FBS supplies for the current day")
    @GetMapping("/current-day-quantity")
    public long countSuppliesForCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        return adminFBSSupplyService.countSuppliesForDay(currentDate);
    }
}
