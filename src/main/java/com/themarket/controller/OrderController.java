package com.themarket.controller;

import com.themarket.dto.OrderRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "주문 API", description = "주문 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "여러 상품에 대해 주문을 생성하고, 각 상품의 재고를 차감한다")
    @PostMapping
    public ResponseEntity<ResponseDTO> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        ResponseDTO result = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok().body(result);
    }
}
