package com.themarket.service;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.entity.Product;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO findProduct(String prdNo) {

        // 상품정보 조회
        Optional<Product> optionalProduct = productRepository.findById(prdNo);

        if (!optionalProduct.isPresent()) {
            throw new ResourceNotFoundException("Product not found with id: " + prdNo);
        }

        Product product = optionalProduct.get();

        return ProductResponseDTO.builder()
                .prdNo(product.getPrdNo())
                .prdNm(product.getPrdNm())
                .prdPrc(product.getPrdPrc())
                .prdStock(product.getPrdStock())
                .build();
    }
}
