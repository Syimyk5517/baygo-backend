package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.BuyerOrderHistoryDetailResponse;
import com.example.baygo.db.dto.response.BuyerOrdersHistoryResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.fbs.FBSOrdersResponse;
import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.FBBOrderResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.db.model.Order;
import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.repository.custom.CustomOrderRepository;
import com.example.baygo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final JwtService jwtService;
    private final OrderRepository orderRepository;

    @Override
    public AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameOfTime) {
        User user = jwtService.getAuthenticate();
        Seller seller = user.getSeller();
        return customOrderRepository.getWeeklyAnalysis(startDate, endDate, warehouseId, nameOfTime, seller.getId());
    }

    @Override
    public List<OrderWareHouseResponse> getAllOrdersByWareHouse() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return customOrderRepository.getAllOrders(sellerId);
    }

    @Override
    public List<RecentOrdersResponse> getResentOrders() {
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        return orderRepository.getAllRecentOrders(sellerId);
    }

    @Override
    public PaginationResponse<FBBOrderResponse> getAllOrdersByFilter(int page, int size, String keyword, OrderStatus orderStatus) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "dateOfOrder"));
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Page<FBBOrderResponse> orderResponses = orderRepository.getAllOrders(sellerId, keyword, orderStatus, pageable);
        return new PaginationResponse<>(orderResponses.getContent(),
                orderResponses.getNumber() + 1,
                orderResponses.getTotalPages());
    }

    @Override
    public PaginationResponse<FBSOrdersResponse> getAllFbsOrdersOnPending(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Long sellerId = jwtService.getAuthenticate().getSeller().getId();
        Page<FBSOrdersResponse> orderResponses = orderRepository.getAllOrdersFbs(sellerId, keyword, pageable);
        return new PaginationResponse<>(orderResponses.getContent(),
                orderResponses.getNumber() + 1,
                orderResponses.getTotalPages());


    }

    @Override
    public List<BuyerOrdersHistoryResponse> getAllHistoryOfOrder(String keyWord) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        return orderRepository.getAllHistoryOfOrder(buyer.getId(), keyWord);
    }

    @Override
    public BuyerOrderHistoryDetailResponse getOrderById(Long orderId) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException("Заказ с идентификатором: " + orderId + " не найден!"));
        if (!buyer.getOrders().contains(order)) {
            throw new BadRequestException("Заказ с идентификатором: " + orderId + " не пренадлежит вам!");
        }
        BuyerOrderHistoryDetailResponse orderHistory = orderRepository.getHistoryOfOrderById(orderId);
        orderHistory.addToProduct(orderRepository.getProductOfOrderByOrderId(orderId));
        return orderHistory;
    }
}