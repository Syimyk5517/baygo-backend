package com.example.baygo.service.impl;


import com.example.baygo.db.dto.request.AccessCardRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.AccessCard;
import com.example.baygo.db.model.Supply;
import com.example.baygo.repository.SupplyRepository;
import com.example.baygo.service.AccessCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessCardServiceImpl implements AccessCardService {
    private final SupplyRepository supplyRepository;


    @Override
    public SimpleResponse save(AccessCardRequest accessCardRequest, Long supplyId) {
        Supply supply = supplyRepository.findById(supplyId)
                .orElseThrow(() -> new NotFoundException("Поставки не найдены!!"));

        AccessCard accessCard = new AccessCard();
        accessCard.setDriverFirstName(accessCard.getDriverFirstName());
        accessCard.setDriverLastName(accessCard.getDriverLastName());
        accessCard.setCarBrand(accessCard.getCarBrand());
        accessCard.setNumberOfCar(accessCard.getNumberOfCar());
        accessCard.setSupplyType(accessCard.getSupplyType());
        supply.setAccessCard(accessCard);

        supplyRepository.save(supply);

        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("поставщик успешно сохранено!!!").build();
    }


}
