package com.themarket;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.entity.Product;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.repository.ProductRepository;
import com.themarket.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("상품조회 성공")
    @Test
    public void findProductSuccess(){
        // Given
        String prdNo = "PD240831001";
        String prdNm = "스팸";
        int prdPrc = 10000;
        int prdStock = 500;

        Product product = Product.builder()
                .prdNo(prdNo)
                .prdNm(prdNm)
                .prdPrc(prdPrc)
                .prdStock(prdStock)
                .build();

        when(productRepository.findById(prdNo)).thenReturn(Optional.of(product));

        // when
        ProductResponseDTO productResponseDTO = productService.findProduct(prdNo);

        // then
        assertNotNull(productResponseDTO);
        assertEquals(prdNo, productResponseDTO.getPrdNo());
        assertEquals(prdNm, productResponseDTO.getPrdNm());
        assertEquals(prdPrc, productResponseDTO.getPrdPrc());
        assertEquals(prdStock, productResponseDTO.getPrdStock());
    }

    @DisplayName("상품조회 실패")
    @Test
    public void findProductFail(){
        // Given
        String prdNo = "PD24083100111";

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.findProduct(prdNo));
        assertEquals("Product not found with id: " + prdNo, exception.getMessage());
    }
}
