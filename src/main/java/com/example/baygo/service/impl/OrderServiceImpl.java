package com.example.baygo.service.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.request.order.BuyerOrderRequest;
import com.example.baygo.db.dto.request.order.ProductOrderRequest;
import com.example.baygo.db.dto.response.BuyerOrderHistoryDetailResponse;
import com.example.baygo.db.dto.response.BuyerOrdersHistoryResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SimpleResponse;
import com.example.baygo.db.dto.response.fbs.OrdersResponse;
import com.example.baygo.db.dto.response.fbs.FBSOrdersResponse;
import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.FBBOrderResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.db.dto.response.orders.RecentOrdersResponse;
import com.example.baygo.db.exceptions.BadRequestException;
import com.example.baygo.db.exceptions.NotFoundException;
import com.example.baygo.db.model.*;
import com.example.baygo.db.model.enums.OrderStatus;
import com.example.baygo.repository.OrderRepository;
import com.example.baygo.repository.OrderSizeRepository;
import com.example.baygo.repository.SizeRepository;
import com.example.baygo.repository.custom.CustomOrderRepository;
import com.example.baygo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final CustomOrderRepository customOrderRepository;
    private final JwtService jwtService;
    private final OrderRepository orderRepository;
    private final OrderSizeRepository orderSizeRepository;
    private final SizeRepository sizeRepository;

    @Override
    public SimpleResponse saveBuyerOrder(BuyerOrderRequest buyerOrderRequest) {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        Order order = new Order();
        order.setDateOfOrder(LocalDateTime.now());
        order.setBuyer(buyer);
        List<OrderSize> orderSizeList = new ArrayList<>();
        for (ProductOrderRequest productOrderRequest : buyerOrderRequest.productOrderRequests()) {
            Size size = sizeRepository.findById(productOrderRequest.sizeId()).orElseThrow(
                    () -> new NotFoundException(String.format("Продукт идентификатором %s не найден.", productOrderRequest.sizeId())));
            if (sizeRepository.isFbb(size.getId())) {
                if (size.getFbbQuantity() >= productOrderRequest.quantityProduct()) {
                    OrderSize orderSize = new OrderSize();
                    orderSize.setFbsOrder(false);
                    orderSize.setFbbOrder(true);
                    orderSize.setOrder(order);
                    orderSize.setOrderStatus(OrderStatus.PENDING);
                    orderSize.setPercentOfDiscount(productOrderRequest.percentOfDiscount());
                    orderSize.setFbbQuantity(productOrderRequest.quantityProduct());
                    size.setFbbQuantity(size.getFbbQuantity() - productOrderRequest.quantityProduct());
                    sizeRepository.save(size);
                    orderSizeRepository.save(orderSize);
                    orderSizeList.add(orderSize);
                } else {
                    int fbsQuantity = productOrderRequest.quantityProduct() - size.getFbbQuantity();
                    OrderSize orderSize = new OrderSize();
                    orderSize.setOrder(order);
                    orderSize.setOrderStatus(OrderStatus.PENDING);
                    orderSize.setFbbOrder(true);
                    orderSize.setFbsOrder(true);
                    orderSize.setFbbQuantity(size.getFbbQuantity());
                    orderSize.setFbsQuantity(fbsQuantity);

                    orderSize.setPercentOfDiscount(productOrderRequest.percentOfDiscount());

                }
            }

        }
        return null;
    }

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