# 온라인 쇼핑몰 상품 재고 차감 API

## 기술 스택
- Java 17
- Spring Boot 3.3.3
- JPA (Hibernate)
- H2 Database (테스트용)
- Gradle
- JUnit 5 (테스트)
- Swagger (API 문서화)

## 프로젝트 설정 및 실행 방법

### 1. 빌드 및 실행
```bash
./gradlew build
./gradlew bootRun
```
### 2. Swagger 를 통한 API 테스트
애플리케이션 실행 후 Swagger UI 를 통해 API를 테스트할 수 있습니다.
- URL : http://localhost:8080/theMarket/swagger-ui/index.html

### 3. 동시성 테스트 
주문 API의 동시성 문제를 해결하기 위해 상품 재고 차감 로직에 비관적 락을 사용했습니다.
OrderMultiThreadTest.java에서 멀티스레드 환경에서의 동작을 검증할 수 있습니다.

### 4. H2 데이터베이스 접속
애플리케이션 실행 후 H2 데이터베이스 콘솔을 통해 DB 상태를 확인할 수 있습니다.
- URL : http://localhost:8080/h2-console
- JDBC URL : 'jdbc:h2:mem:test'
- username: 'sa'

## 주요 기능
- **상품 정보 조회**
  - URI : '/api/products/{prdNo}' 
  - 특정 상품의 재고를 포함한 상세 정보를 조회합니다.
- **재고 차감**
  - URI : '/api/products/stock/decrease'
  - 특정 상품의 재고를 차감합니다.
- **주문 생성**
  - URI : '/api/orders'
  - 여러 상품에 대해 주문을 생성하고, 각 상품의 재고를 차감합니다.

## 프로젝트 구조
```
src
└── main
    ├── java
    │   └── com.themarket
    │       ├── TheMarketApplication.java
    │       ├── controller
    │       │   ├── OrderController.java
    │       │   └── ProductController.java
    │       ├── dto
    │       │   ├── OrderRequestDTO.java
    │       │   ├── ProductResponseDTO.java
    │       │   ├── ProductStockDecreaseRequestDTO.java
    │       │   └── ResponseDTO.java
    │       ├── entity
    │       │   ├── Order.java
    │       │   ├── OrderProduct.java
    │       │   └── Product.java
    │       ├── enums
    │       │   └── BaseEnum.java
    │       ├── exception
    │       │   ├── GlobalExceptionHandler.java
    │       │   ├── ValidationException.java
    │       │   └── ResourceNotFoundException.java
    │       ├── repository
    │       │   ├── OrderProductRepository.java
    │       │   ├── OrderRepository.java
    │       │   └── ProductRepository.java
    │       ├── service
    │       │   ├── OrderService.java
    │       │   └── ProductService.java
    │       └── config
    │           └── SwaggerConfig.java
    └── resources
        ├── application.properties
        └── data.sql
test
└── java
    └── com.themarket
        ├── OrderMultiThreadTest.java
        └── ProductServiceTest.java
```

## Contact
- e-mail : lupmika@naver.com