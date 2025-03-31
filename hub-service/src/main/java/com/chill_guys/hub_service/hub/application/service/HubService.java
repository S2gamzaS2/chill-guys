package com.chill_guys.hub_service.hub.application.service;

import com.chill_guys.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubListResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubService {

  HubCreateResponseDto createHub(HubCreateRequestDto requestDto, String userIdHeader);

  HubDetailResponseDto getHubDetail(UUID hubId);

  Page<HubListResponseDto> getHubList(Pageable pageable);

  Page<HubListResponseDto> getSearchHubs(String hubName, String address, UUID hubId, Pageable pageable);

  HubUpdateResponseDto updateHub(UUID hubId, HubUpdateRequestDto requestDto, String userIdHeader);

  void deleteHub(Long userId, UUID hubId,String userIdHeader);
}
