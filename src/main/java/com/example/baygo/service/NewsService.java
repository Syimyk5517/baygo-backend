package com.example.baygo.service;

import com.example.baygo.db.dto.response.NewsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {
    List<NewsResponse> getAllNews();
}
