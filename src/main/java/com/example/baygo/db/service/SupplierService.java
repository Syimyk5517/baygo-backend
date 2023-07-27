package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.SupplierRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface SupplierService {
    SimpleResponse save(SupplierRequest supplierRequest, Long supplyId);
}
