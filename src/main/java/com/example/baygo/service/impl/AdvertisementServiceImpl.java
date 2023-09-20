package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.seller.AdvertisementSaveRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Advertisement;
import com.example.baygo.db.model.Banner;
import com.example.baygo.db.model.Category;
import com.example.baygo.db.model.Seller;
import com.example.baygo.repository.AdvertisementRepository;
import com.example.baygo.repository.BannerRepository;
import com.example.baygo.repository.CategoryRepository;
import com.example.baygo.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final CategoryRepository categoryRepository;
    private final BannerRepository bannerRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse saveAdvertisement(AdvertisementSaveRequest saveRequest) {
        Seller seller = jwtService.getAuthenticate().getSeller();
        Category category = categoryRepository.findById(saveRequest.categoryId())
                .orElseThrow(() -> new NotFoundException(String.format("Категория по номеру %s не найдена", saveRequest.categoryId())));

        Banner banner = new Banner();
        banner.setImage(saveRequest.photo());
        bannerRepository.save(banner);


        Advertisement advertisement = Advertisement.builder()
                .brand(saveRequest.brand())
                .advertisementPlace(saveRequest.advertisementPlace())
                .startDate(saveRequest.startPromotion())
                .finishDate(saveRequest.endPromotion())
                .companyBudget(saveRequest.companyBudget())
                .costPerMillennium(saveRequest.costPerMillennium())
                .displayForecast(saveRequest.displayForecast())
                .category(category)
                .banner(banner)
                .seller(seller)
                .subProductId(saveRequest.subProductId())
                .build();


        advertisement.setIsDay(saveRequest.isDay());
        advertisement.setIsNew(saveRequest.isNew());

        advertisementRepository.save(advertisement);
        return new SimpleResponse(HttpStatus.OK, "Реклама успешно сохранена");
    }
}