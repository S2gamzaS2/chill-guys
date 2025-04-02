package com.chill_guys.delivery_service.application.service.impl;

import com.chill_guys.delivery_service.application.dto.response.HubRouteListResponseDto;
import com.chill_guys.delivery_service.application.service.HubRouteService;
import com.chill_guys.delivery_service.infrastructure.client.HubRouteClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubRouteServiceImpl implements HubRouteService {

    private final HubRouteClient hubRouteClient;

    @Override
    public List<HubRouteListResponseDto> getHubRouteList(UUID startHubId, UUID endHubId) {
        return hubRouteClient.getHubRouteList(startHubId, endHubId);
    }
}
