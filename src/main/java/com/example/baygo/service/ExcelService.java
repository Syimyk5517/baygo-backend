package com.example.baygo.service;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.excel.ProductInfoExcelResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface ExcelService {
    SimpleResponse downloadProductInfoExcelTemplate(List<ProductExcelRequest> productExcelRequests, HttpServletResponse httpServletResponse) throws IOException;

    SimpleResponse downloadProductPackageBarcodeTemplate(List<String> productPackageBarcode, HttpServletResponse httpServletResponse) throws IOException;
    List<ProductInfoExcelResponse>  willReceiveAllInformationAboutTheProductAndBoxViaExcel(MultipartFile multipartFile) throws IOException;

}
