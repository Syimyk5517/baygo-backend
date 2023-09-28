package com.example.baygo.api.excel;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.excel.ProductInfoExcelResponse;
import com.example.baygo.service.ExcelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
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

    @PostMapping("/barcode_product")
    public ResponseEntity<byte[]> downloadProductInfoExcelTemplate(@RequestBody List<ProductExcelRequest> productExcelRequests, HttpServletResponse httpServletResponse) throws IOException {
        File excelFile = excelService.downloadProductInfoExcelTemplate(productExcelRequests);

        FileInputStream fileInputStream = new FileInputStream(excelFile);
        byte[] bytes = new byte[(int) excelFile.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=product_info.xls");
        return ResponseEntity.ok().body(bytes);
    }

    @PostMapping("/barcode_box")
    public ResponseEntity<byte[]> downloadProductPackageBarcodeTemplate(@RequestParam(name = "barcode_box") List<String> productPackageBarcode, HttpServletResponse httpServletResponse) throws IOException {
        File excelFile = excelService.downloadProductPackageBarcodeTemplate(productPackageBarcode);

        FileInputStream fileInputStream = new FileInputStream(excelFile);
        byte[] bytes = new byte[(int) excelFile.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=barcode_box.xls");
        return ResponseEntity.ok().body(bytes);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductInfoExcelResponse> willReceiveAllInformationAboutTheProductAndBoxViaExcel(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        return excelService.willReceiveAllInformationAboutTheProductAndBoxViaExcel(multipartFile);
    }
}