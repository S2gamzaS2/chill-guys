package com.chill_guys.hub_service.hubroute.application.service;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import com.chill_guys.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteService {

  HubRouteDetailResponseDto getHubRouteDetail(UUID hubRoutesId);

  Page<HubRouteListResponseDto> getHubRouteList(UUID startHubId, UUID endHubId, Pageable pageable);

  HubRouteUpdateResponseDto updateHubRoute(UUID hubRoutesId, HubRouteUpdateRequestDto requestDto,
      String userIdHeader);

  void deleteHubRoute(Long userId, UUID hubRoutesId, String userIdHeader);

  void updateRoutesForHub(Hub updateHub);

  void autoDeleteHubRoute(UUID hubId, String userIdHeader);
}
