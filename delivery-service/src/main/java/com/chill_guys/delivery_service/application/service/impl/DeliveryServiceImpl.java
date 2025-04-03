package com.chill_guys.delivery_service.application.service.impl;

import com.chill_guys.delivery_service.application.dto.request.OrderDeliveryRequestDto;
import com.chill_guys.delivery_service.application.dto.request.DeliveryUpdateRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryResponseDto;
import com.chill_guys.delivery_service.application.notification.NotificationService;
import com.chill_guys.delivery_service.application.service.*;
import com.chill_guys.delivery_service.application.mapper.DeliveryMapper;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.domain.model.DeliveryRoute;
import com.chill_guys.delivery_service.domain.model.DeliveryStatus;
import com.chill_guys.delivery_service.domain.repository.DeliveryRepository;
import com.chill_guys.delivery_service.domain.repository.DeliveryRouteRepository;
import com.chill_guys.delivery_service.exception.CustomException;
import com.chill_guys.delivery_service.exception.DeliveryErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryRouteServiceImpl deliveryRouteServiceImpl;

    private final CompanyService companyService;
    private final ProductService productService;

    private final DeliveryRouteRepository deliveryRouteRepository;

    @Override
    @Transactional
    public UUID createDelivery(OrderDeliveryRequestDto orderDeliveryRequestDto) {
        // 1. 상품을 보고 출발 허브 결정
        UUID productId = orderDeliveryRequestDto.getProductId();
        UUID departureHubId = productService.getHubIdByProductId(productId);

        // 2. 수령 업체 보고 도착 허브 결정
        UUID destinationHubId = companyService.getHubIdByCompanyId(orderDeliveryRequestDto.getRequestCompanyId());

        // 3. 배송 저장
        Delivery delivery = DeliveryMapper.toEntity(orderDeliveryRequestDto, departureHubId, destinationHubId);
        deliveryRepository.save(delivery);

        deliveryRouteServiceImpl.createDeliveryRoutes(delivery);

        return delivery.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryResponseDto> getDeliveryList() {
        List<Delivery> deliveryList = deliveryRepository.findByAndDeletedAtIsNull();

        List<DeliveryResponseDto> deliveryResponseDtoList = new ArrayList<>();
        for(Delivery delivery : deliveryList) {
            deliveryResponseDtoList.add(DeliveryMapper.toDto(delivery));
        }
        return deliveryResponseDtoList;
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryResponseDto getDelivery(UUID deliveryId) {
        Delivery delivery = findDeliveryById(deliveryId);
        return DeliveryMapper.toDto(delivery);
    }

    @Override
    @Transactional
    public void updateDelivery(UUID deliveryId, DeliveryUpdateRequestDto deliveryUpdateRequestDto) {
        Delivery delivery = findDeliveryById(deliveryId);

        if(delivery.isInfoChangeable()) {
            delivery.updateOf(deliveryUpdateRequestDto);
        } else throw new CustomException(DeliveryErrorCode.DELIVERY_IN_START);
    }

    @Override
    @Transactional
    public void deleteDelivery(UUID deliveryId, String userId) {
        Delivery delivery = findDeliveryById(deliveryId);
        Long id = Long.parseLong(userId);
        delivery.deletedOf(id);

        List<DeliveryRoute> routeList = deliveryRouteRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId);
        for(DeliveryRoute deliveryRoute : routeList) {
            deliveryRoute.deletedOf(id);
        }
    }


    @Override
    @Transactional
    public void changeDeliveryStatus(UUID deliveryId, DeliveryStatus deliveryStatus) {
        Delivery delivery = findDeliveryById(deliveryId);

        delivery.changeDeliveryStatus(deliveryStatus);
    }


    private Delivery findDeliveryById(UUID deliveryId) {
        return deliveryRepository.findByIdAndDeletedAtIsNull(deliveryId)
                .orElseThrow(() -> new CustomException(DeliveryErrorCode.DELIVERY_NOT_FOUND));
    }
}
