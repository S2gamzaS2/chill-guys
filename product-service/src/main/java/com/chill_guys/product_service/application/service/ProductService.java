package com.chill_guys.product_service.application.service;

import com.chill_guys.product_service.application.dto.request.ProductCreateRequestDto;
import com.chill_guys.product_service.application.dto.request.ProductUpdateRequestDto;
import com.chill_guys.product_service.application.dto.response.ProductCreateResponseDto;
import com.chill_guys.product_service.application.dto.response.ProductGetResponseDto;
import com.chill_guys.product_service.application.dto.response.ProductSearchResponseDto;
import com.chill_guys.product_service.application.dto.response.ProductUpdateResponseDto;
import com.chill_guys.product_service.application.dto.response.StockUpdateResponseDto;
import com.chill_guys.product_service.domain.entity.Product;
import com.chill_guys.product_service.domain.repository.ProductRepository;
import com.chill_guys.product_service.infrastructure.client.CompanyClient;
import com.chill_guys.product_service.infrastructure.client.HubClient;
import com.chill_guys.product_service.infrastructure.client.dto.response.CompanyDetailResponseDto;
import com.chill_guys.product_service.infrastructure.client.dto.response.HubDetailResponseDto;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CompanyClient companyClient;
    private final HubClient hubClient;

    public ProductService(ProductRepository productRepository, CompanyClient companyClient, HubClient hubClient) {
        this.productRepository = productRepository;
        this.companyClient = companyClient;
        this.hubClient = hubClient;
    }

    @Transactional
    public ProductCreateResponseDto createProduct(ProductCreateRequestDto requestProductCreateDto, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);

        UUID findCompanyId = requestProductCreateDto.getCompanyId();
        UUID findHubId = requestProductCreateDto.getHubId();
        CompanyDetailResponseDto companyResponse = this.companyClient.getCompany(findCompanyId);
        if (companyResponse == null) {
            throw new IllegalArgumentException("회사를 찾을 수 없습니다: " + String.valueOf(findCompanyId));
        } else {
            HubDetailResponseDto hubResponse = this.hubClient.getHub(findHubId);
            if (hubResponse == null) {
                throw new IllegalArgumentException("허브를 찾을 수 없습니다: " + String.valueOf(findHubId));
            } else {

                Product product = Product.builder()
                        .companyId(companyResponse.getId())
                        .hubId(hubResponse.getId())
                        .name(requestProductCreateDto.getName())
                        .stock(requestProductCreateDto.getStock())
                        .createdBy(userId)
                        .updatedBy(userId)
                        .createdAt(new Timestamp(System.currentTimeMillis()))
                        .updatedAt(new Timestamp(System.currentTimeMillis()))
                        .build();

                Product savedProduct = (Product)this.productRepository.save(product);

                return ProductCreateResponseDto.builder()
                        .id(savedProduct.getId())
                        .companyId(savedProduct.getCompanyId())
                        .hubId(savedProduct.getHubId())
                        .name(savedProduct.getName())
                        .stock(savedProduct.getStock())
                        .createdAt(savedProduct.getCreatedAt())
                        .createdBy(savedProduct.getCreatedBy())
                        .updatedAt(savedProduct.getUpdatedAt())
                        .updatedBy(savedProduct.getUpdatedBy())
                        .build();
            }
        }
    }

    @Transactional
    public ProductUpdateResponseDto updateProduct(UUID productId, ProductUpdateRequestDto requestProductUpdateDto, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);

        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + String.valueOf(productId)));
        if (requestProductUpdateDto.getName() != null) {
            product.setName(requestProductUpdateDto.getName());
        }

        if (requestProductUpdateDto.getStock() != null) {
            product.setStock(requestProductUpdateDto.getStock());
        }

        product.setUpdatedBy(userId);
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        Product updatedProduct = (Product)this.productRepository.save(product);
        return ProductUpdateResponseDto.builder().id(updatedProduct.getId()).companyId(updatedProduct.getCompanyId()).hubId(updatedProduct.getHubId()).name(updatedProduct.getName()).stock(updatedProduct.getStock()).updatedAt(updatedProduct.getUpdatedAt()).updatedBy(updatedProduct.getUpdatedBy()).build();
    }

    public Page<ProductSearchResponseDto> searchProducts(String name, UUID companyId, UUID hubId, int page, int size, String sortBy, String order) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort sort = Sort.by(new Sort.Order[]{Order.by(sortBy)});
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productList = this.productRepository.searchProducts(name, companyId, hubId, pageable);
        List<Product> products = productList.getContent();
        List<ProductGetResponseDto> productDtos = (List)products.stream().map((product) -> ProductGetResponseDto.builder().id(product.getId()).companyId(product.getCompanyId()).hubId(product.getHubId()).name(product.getName()).stock(product.getStock()).createdAt(product.getCreatedAt()).createdBy(product.getCreatedBy()).updatedAt(product.getUpdatedAt()).updatedBy(product.getUpdatedBy()).build()).collect(Collectors.toList());
        ProductSearchResponseDto productSearchResponseDto = ProductSearchResponseDto.builder().products(productDtos).page(productList.getNumber()).size(productList.getSize()).build();
        return new PageImpl(List.of(productSearchResponseDto), pageable, productList.getTotalElements());
    }

    public ProductGetResponseDto getProduct(UUID productId) {
        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + String.valueOf(productId)));
        return ProductGetResponseDto.builder().id(product.getId()).companyId(product.getCompanyId()).hubId(product.getHubId()).name(product.getName()).stock(product.getStock()).createdAt(product.getCreatedAt()).createdBy(product.getCreatedBy()).updatedAt(product.getUpdatedAt()).updatedBy(product.getUpdatedBy()).build();
    }

    public Page<ProductSearchResponseDto> getAllProducts(int page, int size, String sortBy, String order) {
        if (size != 10 && size != 30 && size != 50) {
            size = 10;
        }

        Sort sort = Sort.by(new Sort.Order(Sort.Direction.fromString(order), sortBy));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productList = this.productRepository.findAll(pageable);
        List<Product> products = productList.getContent();

        List<ProductGetResponseDto> productDtos = products.stream()
                .map(product -> ProductGetResponseDto.builder()
                        .id(product.getId())
                        .companyId(product.getCompanyId())
                        .hubId(product.getHubId())
                        .name(product.getName())
                        .stock(product.getStock())
                        .createdAt(product.getCreatedAt())
                        .createdBy(product.getCreatedBy())
                        .updatedAt(product.getUpdatedAt())
                        .updatedBy(product.getUpdatedBy())
                        .build())
                .collect(Collectors.toList());

        ProductSearchResponseDto productSearchResponseDto = ProductSearchResponseDto.builder()
                .products(productDtos)
                .page(productList.getNumber())
                .size(productList.getSize())
                .build();

        return new PageImpl<>(List.of(productSearchResponseDto), pageable, productList.getTotalElements());
    }

    @Transactional
    public ResponseEntity<Void> deleteProduct(UUID productId, String userIdHeader) {
        Long userId = Long.parseLong(userIdHeader);

        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다: " + String.valueOf(productId)));
        product.setDeletedBy(userId);
        product.setDeletedAt(new Timestamp(System.currentTimeMillis()));
        this.productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @Transactional
    public StockUpdateResponseDto reduceStock(UUID productId, Long quantity) {
        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        boolean success = product.decreaseStock(quantity);
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        this.productRepository.save(product);
        return new StockUpdateResponseDto(success);
    }

    @Transactional
    public StockUpdateResponseDto addStock(UUID productId, Long quantity) {
        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        boolean success = product.increaseStock(quantity);
        product.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        this.productRepository.save(product);
        return new StockUpdateResponseDto(success);
    }

    public UUID getHubIdByProductId(UUID productId) {
        Product product = (Product)this.productRepository.findById(productId).orElseThrow(() -> new RuntimeException("해당 상품 없음"));
        return product.getHubId();
    }

    public String getProductName(UUID productId) {
        Product product = productRepository.findByIdAndDeletedAtIsNull(productId);
        return product.getName();
    }
}