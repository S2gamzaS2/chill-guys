package com.chill_guys.order_service.application.service;

import com.chill_guys.order_service.infrastructure.client.DeliveryClient;
import com.chill_guys.order_service.infrastructure.client.ProductClient;
import com.chill_guys.order_service.domain.Order;
import com.chill_guys.order_service.domain.OrderStatus;
import com.chill_guys.order_service.application.dto.request.OrderCreateRequestDto;
import com.chill_guys.order_service.infrastructure.client.dto.request.OrderDeliveryRequestDto;
import com.chill_guys.order_service.application.dto.request.OrderUpdateRequestDto;
import com.chill_guys.order_service.infrastructure.client.dto.request.ProductStockRequestDto;
import com.chill_guys.order_service.application.dto.response.OrderDetailResponseDto;
import com.chill_guys.order_service.application.dto.response.PageResponseDto;
import com.chill_guys.order_service.infrastructure.client.dto.response.OrderSlackDto;
import com.chill_guys.order_service.infrastructure.client.dto.response.StockUpdateResponseDto;
import com.chill_guys.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final DeliveryClient deliveryClient;

    @Transactional
    public OrderDetailResponseDto createOrder(OrderCreateRequestDto requestDto, String userIdHeader) {

        Long userId = Long.parseLong(userIdHeader);
        UUID orderId = UUID.randomUUID();

        // 1. 재고 감소 요청
        ProductStockRequestDto stockRequestDto = ProductStockRequestDto.builder()
                .productId(requestDto.getProductId())
                .quantity(requestDto.getQuantity())
                .build();

        StockUpdateResponseDto response = productClient.decreaseStock(requestDto.getProductId(), stockRequestDto);

        if (!response.isSuccess()) {
            throw new RuntimeException("재고 부족");
        }

        UUID deliveryId;
        try {
            // 2. 배송 요청
            OrderDeliveryRequestDto deliveryRequest = OrderDeliveryRequestDto.fromOrderRequest(requestDto, orderId);
            deliveryId = deliveryClient.createDelivery(deliveryRequest, userIdHeader);
        } catch (Exception e) {
            // 2-1. 배송 실패 시 재고 복구
            try {
                productClient.increaseStock(requestDto.getProductId(), requestDto.getQuantity());
            } catch (Exception recoverEx) {
                log.error("재고 복구 실패: {}", recoverEx.getMessage());
            }

            // 2-2. 주문 롤백
            throw new RuntimeException("배송 생성 실패로 주문을 완료할 수 없습니다.");
        }

        // 3. 주문 저장
        Order order = Order.toEntity(orderId, requestDto, deliveryId, userId);
        orderRepository.save(order);

        return OrderDetailResponseDto.fromEntity(order);
    }

    // [조회]
    @Transactional(readOnly = true)
    public PageResponseDto<OrderDetailResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAllByDeletedAtIsNull(pageable);
        return new PageResponseDto<>(orders.map(OrderDetailResponseDto::fromEntity));
    }

    // [개별 조회]
    @Transactional(readOnly = true)
    public OrderDetailResponseDto getOrderById(UUID orderId) {
        return orderRepository.findByOrderIdAndDeletedAtIsNull(orderId)
                .map(OrderDetailResponseDto::fromEntity)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // [주문 수정]
    @Transactional
    public OrderDetailResponseDto updateOrder(UUID id, OrderUpdateRequestDto updateDto, String userIdHeader) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        Long userId = Long.parseLong(userIdHeader); //변환

        order.updateOrder(updateDto.getStatus(), updateDto.getQuantity(), updateDto.getRequest(), userId);

        return OrderDetailResponseDto.fromEntity(order);
    }

    // [삭제]
    @Transactional
    public void deleteOrder(UUID id, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader); //변환

        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        // 주문 삭제 시 재고 복구 요청 (값만 전달)
        StockUpdateResponseDto response = productClient.increaseStock(order.getProductId(), order.getQuantity());

        if (!response.isSuccess()) {
            throw new RuntimeException("Stock restore failed");
        }

        // 주문 삭제 처리
        order.deleteOrder(userId);
    }

    // [상태만 수정]
    @Transactional
    public void updateOrderStatus(UUID id, OrderStatus newStatus, String userIdHeader) {
        Order order = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        Long userId = Long.parseLong(userIdHeader);

        order.updateStatus(newStatus, userId);
    }


    public OrderSlackDto getOrderInfo(UUID id) {
        Order order  = orderRepository.findByOrderIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("Order not found or already deleted"));

        Long quantity = order.getQuantity();
        String request = order.getRequest();

        return OrderSlackDto.builder()
                .quantity(quantity)
                .request(request)
                .build();
    }
}
