package com.chill_guys.delivery_service.application.schedule.scheduler;

import com.chill_guys.delivery_service.application.schedule.service.DeliveryAssignmentService;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryAssignmentScheduler {

    private final DeliveryAssignmentService deliveryAssignmentService;

    private volatile boolean shutdown = false;

    @Scheduled(fixedRate = 60000) //1분
    public void runAssignmentScheduler() {
        if(shutdown) return;

        try {
            deliveryAssignmentService.assignCompanyDeliveryManager();
            deliveryAssignmentService.assignHubDeliveryManager();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @PreDestroy
    public void shutdown() {
        shutdown = true;
    }
}
