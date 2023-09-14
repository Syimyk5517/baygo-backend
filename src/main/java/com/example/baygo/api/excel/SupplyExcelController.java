package com.example.baygo.api.excel;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.ExcelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/supply/excel")
@RequiredArgsConstructor
@Tag(name = "Supply Excel API")
@PreAuthorize("hasAuthority('SELLER')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplyExcelController {
    private final ExcelService excelService;

    @PostMapping("/excel")
    @PreAuthorize("hasAuthority('SELLER')")
    public SimpleResponse downloadExcelTemplate(@RequestBody ProductExcelRequest productExcelRequest, HttpServletResponse httpServletResponse) throws IOException {
        return excelService.downloadExcelTemplate(productExcelRequest, httpServletResponse);
    }
}