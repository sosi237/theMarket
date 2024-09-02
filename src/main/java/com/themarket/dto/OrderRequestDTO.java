package com.themarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Schema(title = "주문생성 DTO")
public class OrderRequestDTO {


    @Schema(description = "주문상품 목록")
    private List<OrderProduct> orderProducts;


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder(toBuilder = true)
    public static class OrderProduct {

        @Schema(description = "상품번호", example = "PD240831001")
        private String prdNo;

        @Schema(description = "주문개수", example = "10")
        private int ordQty;
    }

}
