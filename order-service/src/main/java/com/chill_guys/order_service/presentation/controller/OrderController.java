package com.chill_guys.order_service.presentation.controller;

import com.chill_guys.order_service.domain.OrderStatus;
import com.chill_guys.order_service.application.dto.request.OrderCreateRequestDto;
import com.chill_guys.order_service.application.dto.request.OrderUpdateRequestDto;
import com.chill_guys.order_service.application.dto.response.OrderDetailResponseDto;
import com.chill_guys.order_service.application.dto.response.PageResponseDto;
import com.chill_guys.order_service.application.service.OrderService;
import com.chill_guys.order_service.infrastructure.client.dto.response.OrderSlackDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // [등록]
    @PostMapping
    public ResponseEntity<OrderDetailResponseDto> createOrder(@RequestBody OrderCreateRequestDto requestDto, @RequestHeader(value = "X-User-Id") String userIdHeader) {
        return ResponseEntity.ok(orderService.createOrder(requestDto, userIdHeader));
    }

    // [전체 조회]
    @GetMapping
    public ResponseEntity<PageResponseDto<OrderDetailResponseDto>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    // [개별 조회]
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // [수정]
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponseDto> updateOrder(@PathVariable UUID id, @RequestBody OrderUpdateRequestDto updateDto, @RequestHeader(value = "X-User-Id") String userIdHeader) {
        return ResponseEntity.ok(orderService.updateOrder(id, updateDto, userIdHeader));
    }

    // [삭제]
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID id, @RequestHeader(value = "X-User-Id") String userIdHeader) {
        orderService.deleteOrder(id, userIdHeader);
        return ResponseEntity.ok("Order deleted successfully");
    }

    // [상태 수정]
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus status,
            @RequestHeader(value = "X-User-Id") String userIdHeader
    ) {
        orderService.updateOrderStatus(id, status, userIdHeader);
        return ResponseEntity.ok("Order status updated successfully");
    }

    // [아이디로 수량/요청사항 조회]
    @GetMapping("/{id}/info")
    public ResponseEntity<OrderSlackDto> getOrderInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(orderService.getOrderInfo(id));
    }

}
