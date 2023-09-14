package com.example.baygo.service;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ExcelService {
     SimpleResponse downloadExcelTemplate(ProductExcelRequest productExcelRequest , HttpServletResponse httpServletResponse) throws IOException;
}
