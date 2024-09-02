package com.themarket;

import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.entity.Product;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.exception.ValidationException;
import com.themarket.repository.ProductRepository;
import com.themarket.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품재고차감 성공")
    @Test
    @Transactional
    public void decreaseStock_Success() {
        // Given: 테스트할 상품번호와 차감할 재고량
        String prdNo = "PD240831001";
        int decreaseQty = 100;

        // When: decreaseStock 메서드를 호출
        ProductStockDecreaseRequestDTO requestDTO = ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(decreaseQty)
                .build();
        productService.decreaseStock(requestDTO);

        // Then: 재고가 차감되었는지 확인
        Product updatedProduct = productRepository.findById(prdNo)
                .orElseThrow(() -> new RuntimeException("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo));
        assertEquals(400, updatedProduct.getPrdStock(), "재고가 예상한 만큼 차감되지 않았습니다.");
    }

    @DisplayName("상품재고차감 실패: 상품번호 null")
    @Test
    @Transactional
    public void decreaseStockFail_prdNoNull() {
        int decreaseQty = 100;

        ProductStockDecreaseRequestDTO requestDTO = ProductStockDecreaseRequestDTO.builder()
                .decreaseQty(decreaseQty)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> productService.decreaseStock(requestDTO));
        assertEquals("상품번호 혹은 차감개수가 잘못되었습니다.", exception.getMessage());

    }

    @DisplayName("상품재고차감 실패: 음수 개수")
    @Test
    @Transactional
    public void decreaseStockFail_minusQuantity() {
        String prdNo = "PD240831001";
        int decreaseQty = -100;

        ProductStockDecreaseRequestDTO requestDTO = ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(decreaseQty)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> productService.decreaseStock(requestDTO));
        assertEquals("상품번호 혹은 차감개수가 잘못되었습니다.", exception.getMessage());

    }

    @DisplayName("상품재고차감 실패: 존재하지 않는 상품번호")
    @Test
    @Transactional
    public void decreaseStockFail_invalidPrdNo() {
        String prdNo = "PD2408310012345";
        int decreaseQty = 100;

        ProductStockDecreaseRequestDTO requestDTO = ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(decreaseQty)
                .build();
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> productService.decreaseStock(requestDTO));
        assertEquals("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo, exception.getMessage());

    }

    @DisplayName("상품재고차감 실패: 재고부족")
    @Test
    @Transactional
    public void decreaseStockFail_notEnoughStock() {
        // Given: 재고보다 더 많은 수량을 차감하려고 시도
        String prdNo = "PD240831001";
        int decreaseQty = 1000;

        // When & Then: decreaseStock 메서드 호출 시 예외가 발생하는지 확인
        ProductStockDecreaseRequestDTO requestDTO = ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(decreaseQty)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () -> productService.decreaseStock(requestDTO));
        assertEquals("재고가 부족합니다. 차감할 수 없습니다: " + prdNo, exception.getMessage());

    }
}
