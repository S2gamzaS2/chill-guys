package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.request.DeliveryRouteUpdateRequestDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import com.chill_guys.delivery_service.application.dto.response.DeliveryRouteResponseDto;
import com.chill_guys.delivery_service.application.dto.response.HubRouteListResponseDto;
import com.chill_guys.delivery_service.application.mapper.DeliveryRouteMapper;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.domain.model.DeliveryRoute;
import com.chill_guys.delivery_service.domain.model.DeliveryRouteStatus;
import com.chill_guys.delivery_service.domain.repository.DeliveryRouteRepository;
import com.chill_guys.delivery_service.exception.CustomException;
import com.chill_guys.delivery_service.exception.DeliveryErrorCode;
import com.chill_guys.delivery_service.infrastructure.client.DeliveryManagerClient;
import com.chill_guys.delivery_service.infrastructure.client.HubRouteClient;
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
public class DeliveryRouteService {

    private final DeliveryRouteRepository deliveryRouteRepository;
    private final HubRouteClient hubRouteClient;
    private final DeliveryManagerClient deliveryManagerClient;

    @Transactional
    public void createDeliveryRoutes(Delivery delivery) {

        // 1. 출발+도착 허브 보고 허브간 이동경로 조회
        List<HubRouteListResponseDto> hubRouteList = hubRouteClient.getHubRouteList(delivery.getDepartureHubId(), delivery.getDestinationHubId());

        // 2. 조회 된 허브간 이동 경로에서 시작허브, 도착허브 예상거리, 예상 소요시간 조회
        Integer sequence = 1;

        for(HubRouteListResponseDto hubRoute : hubRouteList){
            UUID startHubId = hubRoute.getStartHubId();
            UUID endHubId = hubRoute.getEndHubId();
            Double estimatedDistance = hubRoute.getDeliveryDistance();
            Integer estimatedTime = hubRoute.getDeliveryTime();

            // TODO: 실제 걸린시간 어딘가에서 가져오기..
            Double actualDistance = 212.4677;
            Integer actualTime = 135;

            DeliveryRoute deliveryRoute = DeliveryRouteMapper.toEntity(
                    delivery.getId(),
                    sequence++,
                    startHubId,
                    endHubId,
                    estimatedDistance,
                    estimatedTime,
                    actualDistance,
                    actualTime);

            deliveryRouteRepository.save(deliveryRoute);
        }
    }

    @Transactional(readOnly = true)
    public List<DeliveryRouteResponseDto> getDeliveryRoutesList(UUID deliveryId) {
        List<DeliveryRoute> deliveryRouteList = deliveryRouteRepository.findByDeliveryIdAndDeletedAtIsNull(deliveryId);

        List<DeliveryRouteResponseDto> deliveryRouteResponseDtoList = new ArrayList<>();

        for(DeliveryRoute deliveryRoute : deliveryRouteList){
            deliveryRouteResponseDtoList.add(DeliveryRouteMapper.toDto(deliveryRoute));
        }

        return deliveryRouteResponseDtoList;
    }

    @Transactional(readOnly = true)
    public DeliveryRouteResponseDto getDeliveryRoute(UUID deliveryId, UUID routesId) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(routesId, deliveryId);

        return DeliveryRouteMapper.toDto(deliveryRoute);
    }

    @Transactional
    public void updateDeliveryRoute(UUID deliveryId, UUID routesId, DeliveryRouteUpdateRequestDto deliveryRouteUpdateRequestDto) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(routesId, deliveryId);

        if(deliveryRoute.isInfoChangeable()) {
            deliveryRoute.updateOf(deliveryRouteUpdateRequestDto);

        } else throw new CustomException(DeliveryErrorCode.DELIVERY_IN_START);

        deliveryRouteRepository.save(deliveryRoute);
    }


    @Transactional
    public void deleteDeliveryRoute(UUID deliveryId, UUID routesId, String userId) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(routesId, deliveryId);
        Long id = Long.parseLong(userId);
        deliveryRoute.deletedOf(id);
    }


    @Transactional
    public void assignPendingDeliveries() {
        List<DeliveryRoute> pendingDeliveryies = deliveryRouteRepository.findByStatusAndDeletedAtIsNull(DeliveryRouteStatus.PENDING);

        if(pendingDeliveryies.isEmpty()) {
            return;
        }

        for(DeliveryRoute deliveryRoute : pendingDeliveryies) {
            UUID startHubId = deliveryRoute.getStartHudId();
            UUID endHubId = deliveryRoute.getEndHudId();
            DeliveryManagerInfoDto dto = deliveryManagerClient.assignDeliveryManager(startHubId, endHubId, "COMPANY");
            deliveryRoute.assignHubDeliveryManager(dto.getId());

            log.info("HubDeliveryManager Assigned");
        }
    }

    @Transactional
    public void changeDeliveryStatus(UUID deliveryId, UUID routesId, DeliveryRouteStatus status) {
        DeliveryRoute deliveryRoute = findDeliveryRouteById(routesId, deliveryId);
        deliveryRoute.changeDeliveryStatus(status);
    }


    private DeliveryRoute findDeliveryRouteById(UUID routeId, UUID deliveryId) {
        return deliveryRouteRepository.findByIdAndDeliveryIdAndDeletedAtIsNull(routeId, deliveryId)
                .orElseThrow(() -> new CustomException(DeliveryErrorCode.DELIVERY_ROUTE_NOT_FOUND));
    }
}
