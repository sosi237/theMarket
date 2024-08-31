package com.themarket.service;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.entity.Product;
import com.themarket.enums.BaseEnum;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.exception.ValidationException;
import com.themarket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponseDTO findProduct(String prdNo) {

        // 상품정보 조회
        Optional<Product> optionalProduct = productRepository.findById(prdNo);

        if (!optionalProduct.isPresent()) {
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

    public ResponseDTO decreaseStock(ProductStockDecreaseRequestDTO productStockDecreaseRequestDTO) {
        List<String> prdNos = new ArrayList<>();
        Map<String, Integer> qtyMap = new HashMap<>();

        for(ProductStockDecreaseRequestDTO.Product product : productStockDecreaseRequestDTO.getProducts()){

            if (!StringUtils.hasText(product.getPrdNo()) || product.getDecreaseQty() <= 0) {
                throw new ValidationException("상품번호 혹은 차감개수가 잘못되었습니다.");
            }

            prdNos.add(product.getPrdNo());
            qtyMap.put(product.getPrdNo(), product.getDecreaseQty());
        }

        // 상품 목록 조회
        List<Product> products = productRepository.findAllById(prdNos);

        if (!ObjectUtils.isEmpty(products)){

            for(Product product : products){
                int decreaseQty = qtyMap.get(product.getPrdNo());

                if (product.getPrdStock() < decreaseQty) {
                    throw new ValidationException("재고가 부족합니다. 차감할 수 없습니다: " + product.getPrdNo());
                }

                // 재고 차감처리
                productRepository.save(product.toBuilder()
                        .prdStock(product.getPrdStock() - decreaseQty)
                        .build());
            }
        }

        return ResponseDTO.builder()
                .code(BaseEnum.Success.getCode())
                .message(BaseEnum.Success.getDescription())
                .build();
    }
}
