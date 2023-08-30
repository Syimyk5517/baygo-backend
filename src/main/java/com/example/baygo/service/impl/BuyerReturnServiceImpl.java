package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.buyer.ReturnProductRequest;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.buyer.ReturnGetByIdResponse;

import com.example.baygo.db.dto.response.buyer.ReturnsResponse;
import com.example.baygo.db.exceptions.AlreadyExistException;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Order;
import com.example.baygo.db.model.OrderSize;
import com.example.baygo.db.model.Return;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.repository.OrderSizeRepository;

import com.example.baygo.repository.ReturnRepository;
import com.example.baygo.service.BuyerReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BuyerReturnServiceImpl implements BuyerReturnService {
    private final ReturnRepository returnRepository;
    private final OrderRepository orderRepository;
    private final OrderSizeRepository orderSizeRepository;
    private final JwtService jwtService;
    @Override
    public SimpleResponse save(ReturnProductRequest request) {
        OrderSize orderSize = orderSizeRepository.findById(request.orderSizeId()).orElseThrow(
                () -> new NotFoundException(String.format("Продукт с идентификатором %s не найден!", request.orderSizeId())));
        Order order = orderRepository.findById(request.orderId()).orElseThrow(() -> new NotFoundException("Нет такого заказа!"));
        if (!order.getOrderSizes().contains(orderSize)) {
            throw new NotFoundException("Продукт в данном заказе не найден");
        }
        if (orderSize.getQuantity() < orderSizeRepository.countOfProductsToBeReturned(orderSize.getId())) {
            throw new AlreadyExistException("В уже подали заявку на этот товар!");
        }
        if (orderSize.getQuantity() < orderSizeRepository.countOfProductsToBeReturned(orderSize.getId()) + request.quantityProduct()) {
            throw new BadRequestException("Неверная количества продукт!");
        }
        if (!LocalDate.now().isBefore(ChronoLocalDate.from(order.getDateOfOrder().plusDays(7)))) {
            return SimpleResponse.
                    builder().
                    httpStatus(HttpStatus.BAD_REQUEST).
                    message("Вы не сможете подать заявку так как после получение товара дается 7 дней на проверку!").
                    build();
        }
        Return newReturn = new Return();
        newReturn.setWithDelivery(request.withDelivery());
        newReturn.setReason(request.returnReason());
        newReturn.setImages(request.returnImages());
        newReturn.setCountry(request.country());
        newReturn.setCity(request.city());
        newReturn.setAddress(request.address());
        newReturn.setPostalCode(request.postalCode());
        newReturn.setPhoneNumber(request.phoneNumber());
        newReturn.setOrderSize(orderSize);
        newReturn.setDateOfReturn(LocalDateTime.now());
        returnRepository.save(newReturn);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Возврат успешно оформлено!").build();
    }

    //        LocalDate lastDateForReturn = order.getDateOfReceived().plusDays(7);
    //        System.out.println("lastDateForReturn = " + lastDateForReturn);



    @Override
    public List<ReturnsResponse> getAll() {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        return returnRepository.getAllReturns(buyer.getId());
    }

    @Override
    public ReturnGetByIdResponse getById(Long returnId) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        ReturnGetByIdResponse returnGetByIdResponse = returnRepository.returnGetById(buyer.getId(), returnId);
        List<String> returnImageUrls = returnRepository.getReturnImageById(buyer.getId(), returnId);

        returnGetByIdResponse.setImages(returnImageUrls);

        return returnGetByIdResponse;
    }

}
