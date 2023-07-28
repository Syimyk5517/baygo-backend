package com.example.baygo.service.impl;

import com.example.baygo.db.dto.response.NewsResponse;
import com.example.baygo.repository.NewsRepository;
import com.example.baygo.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Override
    public List<NewsResponse> getAllNews() {
        return newsRepository.getAllNews();
    }
}
