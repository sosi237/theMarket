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
@Schema(title = "상품 재고차감 DTO")
public class ProductStockDecreaseRequestDTO {

    @Schema(description = "상품 목록")
    private List<Product> products;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder(toBuilder = true)
    public static class Product {

        @Schema(description = "상품번호", example = "PD240831001")
        private String prdNo;

        @Schema(description = "차감 개수", example = "10")
        private int decreaseQty;
    }
}
