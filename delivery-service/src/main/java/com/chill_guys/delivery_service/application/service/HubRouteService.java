package com.chill_guys.delivery_service.application.service;

import com.chill_guys.delivery_service.application.dto.response.HubRouteListResponseDto;

import java.util.List;
import java.util.UUID;

public interface HubRouteService {

    List<HubRouteListResponseDto> getHubRouteList(UUID startHubId, UUID endHubId);
}
