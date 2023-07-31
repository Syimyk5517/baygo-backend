package com.example.baygo.service;

import com.example.baygo.db.dto.request.SupplierRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface SupplierService {
    SimpleResponse save(SupplierRequest supplierRequest, Long supplyId);
}
