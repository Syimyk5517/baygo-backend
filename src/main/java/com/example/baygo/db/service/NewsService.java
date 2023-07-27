package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.NewsResponse;

import java.util.List;

public interface NewsService {
    List<NewsResponse> getAllNews();
}
