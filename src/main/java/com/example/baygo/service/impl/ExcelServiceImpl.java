package com.example.baygo.service.impl;

import com.example.baygo.db.dto.request.excel.ProductExcelRequest;
import com.example.baygo.db.dto.response.excel.ProductInfoExcelResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {


    @Override
    public File downloadProductInfoExcelTemplate(List<ProductExcelRequest> productExcelRequests) throws IOException {
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
        return tempFile;


    }

    @Override
    public File downloadProductPackageBarcodeTemplate(List<String> productPackageBarcode) throws IOException {
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

        return tempFile;
    }

    @Override
    public List<ProductInfoExcelResponse> willReceiveAllInformationAboutTheProductAndBoxViaExcel(MultipartFile multipartFile) {
        List<ProductInfoExcelResponse> productInfoExcelResponses = new ArrayList<>();
        if (multipartFile.isEmpty()) {
            throw new BadRequestException("Ваш excel файл пустой.");
        }
        try (InputStream is = multipartFile.getInputStream()) {

            String fileName = multipartFile.getOriginalFilename();
            if (fileName != null && !fileName.endsWith(".xlsx")) {
                throw new BadRequestException("Неверный формат файла. Пожалуйста, загрузите файл в формате .xlsx.");
            }
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            XSSFSheet sheet = workbook.getSheetAt(0);

            boolean skipHeader = true;

            for (Row row : sheet) {
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String barcodeBox = row.getCell(0).getStringCellValue();
                String barcodeProduct = row.getCell(1).getStringCellValue();
                String quantityProduct = row.getCell(2).getStringCellValue();

                ProductInfoExcelResponse infoExcelResponse = ProductInfoExcelResponse.builder()
                        .barcodeProduct(barcodeProduct)
                        .quantityProduct(quantityProduct)
                        .barcodeBox(barcodeBox).build();
                productInfoExcelResponses.add(infoExcelResponse);
            }
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return productInfoExcelResponses;
    }
}


