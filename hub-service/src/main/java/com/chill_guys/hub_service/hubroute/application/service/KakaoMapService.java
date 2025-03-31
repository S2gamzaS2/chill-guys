package com.chill_guys.hub_service.hubroute.application.service;

import com.chill_guys.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import java.util.List;

public interface KakaoMapService {

  HubRouteCreateResponseDto createHubRoute(HubRouteCreateRequestDto requestDto,
      String userIdHeader);

  List<HubRouteCreateResponseDto> autoCreateHubRoute(String userIdHeader);

}
