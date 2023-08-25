package com.example.baygo.service;

import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.AppealResponse;

public interface AppealService {
    SimpleResponse saveAppeal(AppealResponse appealResponse);
}
