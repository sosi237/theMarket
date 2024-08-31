package com.themarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Schema(title = "상품조회 DTO")
public class ProductResponseDTO {

    @Schema(description = "상품번호", example = "PD240831001")
    private String prdNo;

    @Schema(description = "상품명", example = "스팸")
    private String prdNm;

    @Schema(description = "상품가격", example = "10000")
    private int prdPrc;

    @Schema(description = "상품재고", example = "500")
    private int prdStock;
}
