package com.example.baygo.api.admin;

import com.example.baygo.db.dto.request.admin.FBBStatusChangeRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.admin.AdminSupplyGetAllResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.service.AdminFBBSupplyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin/fbb/supplies")
@RequiredArgsConstructor
@Tag(name = "Admin FBB Status Change Api")
@CrossOrigin(value = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminFBBStatusChangeController {

    private final AdminFBBSupplyService adminFBBSupplyService;

    @Operation(summary = "Change status of multiple FBB supplies by admin")
    @PostMapping("/status-change")
    public SimpleResponse fbbStatusChange(@RequestBody FBBStatusChangeRequest request) {
        adminFBBSupplyService.fbbSupplyChangeStatus(request.getSupplyIds(), request.getNewStatus());
        return new SimpleResponse(HttpStatus.OK, String.format("FBB статус поставки изменен на %s успешно", request.getNewStatus()));
    }

    @Operation(summary = "Get all FBB supply status", description = "This method will get all status")
    @GetMapping("/statuses")
    public List<SupplyStatus> getAllStatus() {
        return adminFBBSupplyService.getAllStatus();
    }

    @Operation(summary = "Get all FBB supplies", description = "This method wil get all supplies")
    @GetMapping
    public PaginationResponse<AdminSupplyGetAllResponse> getAllSupplies(@RequestParam(required = false) String keyWord,
                                                                        @RequestParam(defaultValue = "1") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return adminFBBSupplyService.getAllSupplies(keyWord, size, page);
    }

    @Operation(summary = "Count total FBB supply quantity")
    @GetMapping("/total-quantity")
    public long countTotalSupplyQuantity() {
        return adminFBBSupplyService.countTotalSupplyQuantity();
    }

    @Operation(summary = "Count FBB supplies for the current day")
    @GetMapping("/current-day-quantity")
    public long countSuppliesForCurrentDay() {
        LocalDate currentDate = LocalDate.now();
        return adminFBBSupplyService.countSuppliesForDay(currentDate);
    }


}
