package com.themarket.service;

import com.themarket.dto.OrderRequestDTO;
import com.themarket.dto.ProductResponseDTO;
import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.entity.Order;
import com.themarket.entity.OrderProduct;
import com.themarket.enums.BaseEnum;
import com.themarket.exception.ValidationException;
import com.themarket.repository.OrderProductRepository;
import com.themarket.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;

    private final ProductService productService;

    public ResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

        // 주문정보 생성
        Order order = orderRepository.save(
                Order.builder()
                        .regDt(LocalDateTime.now())
                        .build()
        );

        long ordSn = order.getOrdSn();

        long ordPrdTurn = 1;
        for (OrderRequestDTO.OrderProduct orderProduct : orderRequestDTO.getOrderProducts()) {
            String prdNo = orderProduct.getPrdNo();

            // 재고차감
            productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                    .prdNo(prdNo)
                    .decreaseQty(orderProduct.getOrdQty())
                    .build());

            ProductResponseDTO productResponseDTO = productService.findProduct(prdNo);

            // 주문상품 추가
            orderProductRepository.save(
                    OrderProduct.builder()
                            .orderProductId(OrderProduct.OrderProductId.builder()
                                    .ordSn(ordSn)
                                    .ordPrdTurn(ordPrdTurn++)
                                    .build())
                            .prdNo(prdNo)
                            .ordPrdNm(productResponseDTO.getPrdNm())
                            .ordPrdPrc(productResponseDTO.getPrdPrc())
                            .ordPrdQty(orderProduct.getOrdQty())
                            .build()
            );
        }

        return ResponseDTO.builder()
                .code(BaseEnum.Success.getCode())
                .message(BaseEnum.Success.getDescription())
                .build();
    }
}
