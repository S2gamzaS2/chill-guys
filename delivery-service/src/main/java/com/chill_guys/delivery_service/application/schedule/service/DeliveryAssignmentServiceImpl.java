package com.chill_guys.delivery_service.application.schedule.service;

import com.chill_guys.delivery_service.application.dto.response.DeliveryManagerInfoDto;
import com.chill_guys.delivery_service.application.notification.NotificationService;
import com.chill_guys.delivery_service.application.service.DeliveryManagerService;
import com.chill_guys.delivery_service.domain.model.Delivery;
import com.chill_guys.delivery_service.domain.model.DeliveryRoute;
import com.chill_guys.delivery_service.domain.model.DeliveryRouteStatus;
import com.chill_guys.delivery_service.domain.model.DeliveryStatus;
import com.chill_guys.delivery_service.domain.repository.DeliveryRepository;
import com.chill_guys.delivery_service.domain.repository.DeliveryRouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryAssignmentServiceImpl implements DeliveryAssignmentService{

    DeliveryRepository deliveryRepository;
    DeliveryRouteRepository deliveryRouteRepository;

    NotificationService notificationService;
    DeliveryManagerService deliveryManagerService;

    @Override
    @Transactional
    public void assignCompanyDeliveryManager() {
        List<Delivery> pendingDeliveryies = deliveryRepository.findByDeliveryStatusAndDeletedAtIsNull(DeliveryStatus.PENDING);

        if(pendingDeliveryies.isEmpty()) {
            return;
        }

        for(Delivery delivery : pendingDeliveryies) {
            UUID departureHubId = delivery.getDepartureHubId();
            UUID destinationHubId = delivery.getDestinationHubId();
            String type = "Company";
            DeliveryManagerInfoDto dto = deliveryManagerService.assignDeliveryManager(departureHubId, destinationHubId, type);
            delivery.assignDeliveryManager(dto.getId());

            log.info("Company Delivery Manager Assigned");

            // 배송정보를 kafka로 전송
            notificationService.sendMessage(type, delivery, dto.getSlackId(), delivery.getRecipientCompany().getAddress());

        }
    }

    @Override
    @Transactional
    public void assignHubDeliveryManager() {
        List<DeliveryRoute> pendingDeliveries = deliveryRouteRepository.findByStatusAndDeletedAtIsNull(DeliveryRouteStatus.PENDING);

        if(pendingDeliveries.isEmpty()) {
            return;
        }

        for(DeliveryRoute deliveryRoute : pendingDeliveries) {
            UUID startHubId = deliveryRoute.getStartHudId();
            UUID endHubId = deliveryRoute.getEndHudId();
            DeliveryManagerInfoDto dto = deliveryManagerService.assignDeliveryManager(startHubId, endHubId, "HUB");
            deliveryRoute.assignHubDeliveryManager(dto.getId());

            log.info("Hub Delivery Manager Assigned");
        }
    }
}
