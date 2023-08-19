package com.example.baygo.service.impl;

import com.example.baygo.db.model.Banner;
import com.example.baygo.repository.BannerRepository;
import com.example.baygo.service.BannerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;
    @Override
    public List<Banner> getAllBanners() {
      return bannerRepository.findAll();
    }
}
