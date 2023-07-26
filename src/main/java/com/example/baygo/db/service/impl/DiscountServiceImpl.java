package com.example.baygo.db.service.impl;

import com.example.baygo.db.dto.request.DiscountRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.model.Discount;
import com.example.baygo.db.model.SubProduct;
import com.example.baygo.db.repository.DiscountRepository;
import com.example.baygo.db.repository.SubProductRepository;
import com.example.baygo.db.service.DiscountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final SubProductRepository subProductRepository;

    @Override
    public SimpleResponse saveDiscount(DiscountRequest request) {
        List<SubProduct> subProducts = subProductRepository.findAllById(request.subProductsId());
        Discount discount = new Discount();
        discount.setDateOfFinish(request.dateOfFinish());
        discount.setDateOfStart(LocalDateTime.now().withSecond(0).withNano(0));
        discount.setPercent(request.percent());
        discount.setNameOfDiscount(request.nameOfDiscount());
        for (SubProduct subProduct : subProducts) {
            subProduct.setDiscount(discount);
        }
        discountRepository.save(discount);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Скидка успешно сохранена!").build();
    }

    //cron = "0 0/5 * * * ?" every 5 minute
//cron = "0 0 0 * * ?" every day
//cron = "0 0 * * * ?" every hour
    @Scheduled(cron = "0 0 * * * ?")
    @Override
    public void deleteExpiredDiscount() {
        List<Discount> discounts = discountRepository.findByDateOfFinishIsLessThanEqual(LocalDateTime.now().withSecond(0).withNano(0));
        for (Discount discount : discounts) {
            for (SubProduct subProduct : discount.getSubProducts()) {
                subProduct.setDiscount(null);
                subProductRepository.save(subProduct);
            }
            discountRepository.delete(discount);
        }
        System.out.println("Скидка успешно удалена!");
    }
}
