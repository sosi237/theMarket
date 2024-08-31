package com.themarket;

import com.themarket.dto.ProductResponseDTO;
import com.themarket.dto.ProductStockDecreaseRequestDTO;
import com.themarket.dto.ResponseDTO;
import com.themarket.entity.Product;
import com.themarket.enums.BaseEnum;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.repository.ProductRepository;
import com.themarket.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    public void findProductSuccess() {
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
    public void findProductFail() {
        // Given
        String prdNo = "PD24083100111";

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.findProduct(prdNo));
        assertEquals("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo, exception.getMessage());
    }


    @DisplayName("상품 재고차감 성공")
    @Test
    public void decreaseStockSuccess() {
        // given
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
        ResponseDTO responseDTO = productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(10)
                .build());

        // then
        assertNotNull(responseDTO);
        assertEquals(BaseEnum.Success.getCode(), responseDTO.getCode());
    }

    @DisplayName("상품 재고차감 실패: prdNo 없음")
    @Test
    public void decreaseStockFail_emptyPrdNo() {
        ResponseDTO responseDTO = productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                .decreaseQty(10)
                .build());

        assertEquals(BaseEnum.Fail.getCode(), responseDTO.getCode());
        assertEquals("상품번호 혹은 차감개수가 잘못되었습니다.", responseDTO.getMessage());
    }

    @DisplayName("상품 재고차감 실패: 차감개수 음수")
    @Test
    public void decreaseStockFail_wrongQuantity() {
        ResponseDTO responseDTO = productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                .prdNo("PD240831001")
                .decreaseQty(-10)
                .build());

        assertEquals(BaseEnum.Fail.getCode(), responseDTO.getCode());
        assertEquals("상품번호 혹은 차감개수가 잘못되었습니다.", responseDTO.getMessage());
    }

    @DisplayName("상품 재고차감 실패: 존재하지 않는 상품")
    @Test
    public void decreaseStockFail_wrongPrdNo() {
        String prdNo = "PD24083100111";

        when(productRepository.findById(anyString())).thenReturn(Optional.empty());

        ResponseDTO responseDTO = productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(10)
                .build());

        assertEquals(BaseEnum.Fail.getCode(), responseDTO.getCode());
        assertEquals("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo, responseDTO.getMessage());
    }

    @DisplayName("상품 재고차감 실패: 재고부족")
    @Test
    public void decreaseStockFail_notEnoughStock() {
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

        ResponseDTO responseDTO = productService.decreaseStock(ProductStockDecreaseRequestDTO.builder()
                .prdNo(prdNo)
                .decreaseQty(1000)
                .build());

        assertEquals(BaseEnum.Fail.getCode(), responseDTO.getCode());
        assertEquals("재고가 부족합니다. 차감할 수 없습니다: " + prdNo, responseDTO.getMessage());
    }
}
