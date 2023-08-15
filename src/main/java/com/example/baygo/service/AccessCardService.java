package com.example.baygo.service;

import com.example.baygo.db.dto.request.SupplierRequest;
import com.example.baygo.db.dto.request.fbs.AccessCardRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface AccessCardService {
    SimpleResponse save(SupplierRequest supplierRequest, Long supplyId);
}
