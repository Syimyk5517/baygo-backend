package com.example.baygo.db.service.impl;

import com.example.baygo.db.config.jwt.JwtService;
import com.example.baygo.db.custom.CustomOrderRepository;
import com.example.baygo.db.exceptions.BadCredentialException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.repository.OrderRepository;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.responses.SimpleResponse;
import com.example.baygo.db.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final OrderRepository orderRepository;
    private final JwtService jwtService;


    @Override
    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyWord, Status status) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return customOrderRepository.getAll(page, size, keyWord, status, seller.getId());
    }

    @Override
    public SimpleResponse deleteById(Long orderId) {
        User user = jwtService.getAuthenticate();
        if (user == null) {
            return SimpleResponse.builder()
                    .message("Authentication required")
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        Seller seller = user.getSeller();
        if (seller == null) {
            return SimpleResponse.builder()
                    .message("Seller not found")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        try {
            orderRepository.deleteById(orderId);
            return SimpleResponse.builder()
                    .message(String.format("Order %s deleted successfully", orderId))
                    .status(HttpStatus.OK)
                    .build();
        } catch (NotFoundException e) {
            return SimpleResponse.builder()
                    .message(String.format("Order with ID %s not found", orderId))
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (BadCredentialException e) {
            return SimpleResponse.builder()
                    .message("Failed to delete order")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public AnalysisResponse getWeeklyAnalisys(Date startDate, Date endDate, Long warehouseId, String nameOfTime) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
          return customOrderRepository.getWeeklyAnalysis(startDate,endDate,warehouseId,nameOfTime, seller.getId());
    }


}
