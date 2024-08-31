package com.themarket.controller;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품 API", description = "상품 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품정보 조회", description = "상품정보를 조회한다")
    @GetMapping(value = "/{prdNo}")
    public ResponseEntity<ProductResponseDTO> findProduct(@PathVariable String prdNo) {
        ProductResponseDTO result = productService.findProduct(prdNo);
        return ResponseEntity.ok().body(result);
    }
}
