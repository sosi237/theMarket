package com.themarket.service;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.entity.Product;
import com.themarket.enums.BaseEnum;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.exception.ValidationException;
import com.themarket.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO findProduct(String prdNo) {

        // 상품정보 조회
        Optional<Product> optionalProduct = productRepository.findById(prdNo);

        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo);
        }

        Product product = optionalProduct.get();

        return ProductResponseDTO.builder()
                .prdNo(product.getPrdNo())
                .prdNm(product.getPrdNm())
                .prdPrc(product.getPrdPrc())
                .prdStock(product.getPrdStock())
                .build();
    }

    public void decreaseStock(ProductStockDecreaseRequestDTO productStockDecreaseRequestDTO) {
        final String prdNo = productStockDecreaseRequestDTO.getPrdNo();
        final int decreaseQty = productStockDecreaseRequestDTO.getDecreaseQty();

        if (!StringUtils.hasText(prdNo) || decreaseQty <= 0) {
            throw new ValidationException("상품번호 혹은 차감개수가 잘못되었습니다.");
        }

        Optional<Product> optionalProduct = productRepository.findById(prdNo);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo);
        }

        Product product = optionalProduct.get();

        boolean hasEnoughStock = this.hasEnoughStock(prdNo, decreaseQty);

        if (!hasEnoughStock) {
            throw new ValidationException("재고가 부족합니다. 차감할 수 없습니다: " + prdNo);
        }

        // 재고 차감처리
        productRepository.save(product.toBuilder()
                .prdStock(product.getPrdStock() - decreaseQty)
                .build());

    }

    public boolean hasEnoughStock(String prdNo, int quantity) {

        Optional<Product> optionalProduct = productRepository.findById(prdNo);
        if (optionalProduct.isEmpty()) {
            throw new ResourceNotFoundException("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo);
        }

        Product product = optionalProduct.get();

        return product.getPrdStock() >= quantity;
    }
}
