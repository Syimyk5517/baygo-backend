package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.excel.ProductInfoExcelResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.service.ExcelService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {


    @Override
    public SimpleResponse downloadProductInfoExcelTemplate(List<ProductExcelRequest> productExcelRequests, HttpServletResponse httpServletResponse) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Информация о продуктах");
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Штрих-код продукта");
        headerRow.createCell(1).setCellValue("Количество продукта");

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        HSSFCellStyle barcodeStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        barcodeStyle.setDataFormat(dataFormat.getFormat("0"));

        HSSFCellStyle quantityStyle = workbook.createCellStyle();
        quantityStyle.setAlignment(HorizontalAlignment.CENTER);

        int dataRowIndex = 1;
        for (ProductExcelRequest product : productExcelRequests) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            HSSFCell barcodeCell = dataRow.createCell(0);
            HSSFCell quantityCell = dataRow.createCell(1);

            barcodeCell.setCellValue(product.barcodeProduct());
            barcodeCell.setCellStyle(barcodeStyle);

            quantityCell.setCellValue(product.quantityProduct());
            quantityCell.setCellStyle(quantityStyle);

            dataRowIndex++;
        }

        File tempFile = File.createTempFile("product_info", ".xls");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=product_info.xls");
        return exportExcel(httpServletResponse, tempFile);
    }

    @Override
    public SimpleResponse downloadProductPackageBarcodeTemplate(List<String> productPackageBarcode, HttpServletResponse httpServletResponse) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Штрих-коды Коробы");
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Штрих-код короба");
        sheet.autoSizeColumn(0);

        HSSFCellStyle barcodeStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        barcodeStyle.setDataFormat(dataFormat.getFormat("0"));

        int dataRowIndex = 1;
        for (String barcode : productPackageBarcode) {
            HSSFRow dataRow = sheet.createRow(dataRowIndex);
            HSSFCell barcodeCell = dataRow.createCell(0);
            barcodeCell.setCellValue(barcode);
            barcodeCell.setCellStyle(barcodeStyle);
            dataRowIndex++;
        }
        File tempFile = File.createTempFile("barcode_box", ".xls");
        FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        httpServletResponse.setContentType("application/vnd.ms-excel");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=barcode_box.xls");
        return exportExcel(httpServletResponse, tempFile);
    }

    @Override
    public List<ProductInfoExcelResponse> willReceiveAllInformationAboutTheProductAndBoxViaExcel(MultipartFile multipartFile) throws IOException {
        List<ProductInfoExcelResponse> productInfoExcelResponses = new ArrayList<>();
        if (!multipartFile.isEmpty()) {
            try (InputStream is = multipartFile.getInputStream()) {
                SpreadsheetDocument spreadsheetDocument = SpreadsheetDocument.loadDocument(is);
                Table table = spreadsheetDocument.getSheetByIndex(0);

                boolean skipHeader = true;

                for (Row row : table.getRowList()) {
                    if (skipHeader) {
                        skipHeader = false;
                        continue;
                    }

                    String barcodeBox = row.getCellByIndex(0).getStringValue();
                    String barcodeProduct = row.getCellByIndex(1).getStringValue();
                    String quantityProduct = row.getCellByIndex(2).getStringValue();

                    ProductInfoExcelResponse infoExcelResponse = ProductInfoExcelResponse.builder()
                            .barcodeProduct(barcodeProduct)
                            .quantityProduct(quantityProduct)
                            .barcodeBox(barcodeBox).build();
                    productInfoExcelResponses.add(infoExcelResponse);
                }
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        } else {
            throw new BadRequestException("Ваш excel файл пустой.");
        }

        return productInfoExcelResponses;
    }


    private SimpleResponse exportExcel(HttpServletResponse httpServletResponse, File tempFile) throws IOException {
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


