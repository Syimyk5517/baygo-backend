package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.request.excel.ProductInfoRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.service.ExcelService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {


    @Override
    public SimpleResponse downloadExcelTemplate(ProductExcelRequest productExcelRequest , HttpServletResponse httpServletResponse) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Информация о продуктах");
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Штрих-код продукта");
        headerRow.createCell(1).setCellValue("Количество продукта");

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        HSSFCellStyle barcodeStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        barcodeStyle.setDataFormat(dataFormat.getFormat("0")); // Устанавливаем формат числа без десятичных знаков

        HSSFCellStyle quantityStyle = workbook.createCellStyle();
        quantityStyle.setAlignment(HorizontalAlignment.CENTER);

        int dataRowIndex = 1;
        for (ProductInfoRequest product : productExcelRequest.productRequests()) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            HSSFCell barcodeCell = dataRow.createCell(0);
            HSSFCell quantityCell = dataRow.createCell(1);

            barcodeCell.setCellValue(product.barcodeProduct());
            barcodeCell.setCellStyle(barcodeStyle); // Применяем стиль к ячейке "Штрих-код продукта"

            quantityCell.setCellValue(product.quantityProduct());
            quantityCell.setCellStyle(quantityStyle); // Применяем стиль к ячейке "Количество продукта"

            dataRowIndex++;
        }

        File tempFile = File.createTempFile("product_info", ".xls");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=product_info.xls");

        ServletOutputStream ops = httpServletResponse.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(tempFile);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
            ops.write(buffer, 0, bytesRead);
        }
        ops.flush();
        ops.close();
        fileInputStream.close();

        if (tempFile.exists()) {
            if (tempFile.delete()) {
                System.out.println("Файл Excel успешно удален.");
            } else {
                System.err.println("Ошибка при удалении файла Excel.");
            }
        }

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Файл Excel успешно создан.").build();
    }
}
