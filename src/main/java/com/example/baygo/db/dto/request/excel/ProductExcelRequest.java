package com.example.baygo.db.dto.request.excel;

import java.util.List;

public record ProductExcelRequest(
        List<ProductInfoRequest> productRequests
) {
}
