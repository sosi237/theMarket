package com.themarket.controller;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(value = "/{prdNo}")
    public ResponseEntity<ProductResponseDTO> findProduct(@PathVariable String prdNo) {
        ProductResponseDTO result = productService.findProduct(prdNo);
        return ResponseEntity.ok().body(result);
    }
}
