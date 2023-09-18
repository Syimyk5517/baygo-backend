package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.AppealResponse;
import com.example.baygo.db.model.Appeal;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.repository.AppealRepository;
import com.example.baygo.service.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {
    private final AppealRepository appealRepository;
    private final JwtService jwtService;

    @Override
    public SimpleResponse saveAppeal(AppealResponse appealResponse) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        if (buyer == null) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Требуется аутентификация покупателя")
                    .build();
        }
        Appeal appeal = Appeal.builder()
                .title(appealResponse.title())
                .detailedAppeal(appealResponse.detailOfAppeal())
                .buyer(buyer)
                .build();

        appealRepository.save(appeal);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Обращение успешно сохранено")
                .build();
    }
}

