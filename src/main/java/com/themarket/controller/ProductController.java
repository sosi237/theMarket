package com.themarket.controller;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.enums.BaseEnum;
import com.themarket.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "상품 API", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품정보 조회", description = "특정 상품의 재고를 포함한 상세 정보를 조회한다")
    @GetMapping(value = "/{prdNo}")
    public ResponseEntity<ProductResponseDTO> findProduct(@PathVariable String prdNo) {
        ProductResponseDTO result = productService.findProduct(prdNo);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "상품 재고차감", description = "특정 상품의 재고를 차감한다")
    @PostMapping(value = "/stock/decrease")
    public ResponseEntity<ResponseDTO> decreaseStock(@RequestBody ProductStockDecreaseRequestDTO productStockDecreaseRequestDTO) {
        productService.decreaseStock(productStockDecreaseRequestDTO);
        return ResponseEntity.ok().body(
                ResponseDTO.builder()
                        .code(BaseEnum.Success.getCode())
                        .message(BaseEnum.Success.getDescription())
                        .build()
        );
    }
}
