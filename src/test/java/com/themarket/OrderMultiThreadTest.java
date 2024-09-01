package com.themarket;

import com.themarket.dto.OrderRequestDTO;
import com.themarket.entity.Product;
import com.themarket.exception.ResourceNotFoundException;
import com.themarket.repository.OrderProductRepository;
import com.themarket.repository.OrderRepository;
import com.themarket.repository.ProductRepository;
import com.themarket.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderMultiThreadTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

     private ExecutorService executorService;

    // 10개의 스레드가 동시에 주문 요청을 보냄
    int numberOfThreads = 10;

    @BeforeEach
    public void setup() {
        // 스레드 풀 생성
        executorService = Executors.newFixedThreadPool(numberOfThreads); // 10개의 스레드 풀
    }

    @AfterEach
    public void tearDown() {
        // 스레드 풀 종료
        executorService.shutdown();
    }


    @Test
    public void createOrder() throws InterruptedException {
        // 테스트할 상품의 재고를 100으로 설정
        String prdNo = "PD240831001";
        int initialStock = 500;
        int ordQty = 10;

        Product product = productRepository.findById(prdNo).orElseThrow(() -> new ResourceNotFoundException("해당 상품번호와 일치하는 정보가 없습니다: " + prdNo));
        productRepository.save(product.toBuilder().prdStock(initialStock).build());

        // 각 스레드가 1개의 상품 주문
        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .orderProducts(List.of(OrderRequestDTO.OrderProduct.builder()
                        .prdNo(prdNo)
                        .ordQty(ordQty)
                        .build()))
                .build();
        CountDownLatch latch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    orderService.createOrder(orderRequestDTO);
                } catch (Exception e) {
                    System.out.println("멀티스레드 테스트 중 오류 발생: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 완료될 때까지 대기
        latch.await();

        // 주문 처리 후 재고 확인
        Product updatedProduct = productRepository.findById(prdNo).orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다."));
        int expectedStock = initialStock - numberOfThreads * ordQty;

        assertEquals(expectedStock, updatedProduct.getPrdStock(), "재고가 예상한 만큼 차감되지 않았습니다.");

        // 추가로 주문이 10개 생성되었는지 확인
        long orderCount = orderRepository.count();
        assertEquals(numberOfThreads, orderCount, "주문 개수가 예상된 수와 일치하지 않습니다.");

    }
}
