package com.chill_guys.hub_service.hub.application.service;

import com.chill_guys.hub_service.global.utils.PaginationUtils;
import com.chill_guys.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubListResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.UserRoleSearchResponseDto;
import com.chill_guys.hub_service.hub.domain.entity.Hub;
import com.chill_guys.hub_service.hub.domain.repository.HubRepository;
import com.chill_guys.hub_service.hub.infrastructure.Client.UserClient;
import com.chill_guys.hub_service.hubroute.application.service.HubRouteService;
import com.chill_guys.hub_service.hubroute.application.service.KakaoMapApiServiceImpl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubServiceImpl implements HubService {

  private final HubRepository hubRepository;
  private final KakaoMapApiServiceImpl kakaoMapApiService;
  private final UserClient userClient;
  private final HubRouteService hubRouteService;

  // 허브 생성
  @Transactional
  @Override
  public HubCreateResponseDto createHub(HubCreateRequestDto requestDto, String userIdHeader) {

    Page<UserRoleSearchResponseDto> userRoles = userClient.roleSearchUser("ROLE_HUB_MANAGER",
        Pageable.ofSize(50));

    if (userRoles.isEmpty()) {
      throw new IllegalStateException("허브 매니저 권한을 가진 사용자가 없습니다.");
    }

    List<UserRoleSearchResponseDto> availableManagers = userRoles.stream()
        .filter(user -> !hubRepository.existsByUserId(user.getUserId()))
        .toList();

    if (availableManagers.isEmpty()) {
      throw new IllegalStateException("모든 허브 매니저 유저가 이미 허브에 관리자로 지정되어 있습니다.");
    }

    UserRoleSearchResponseDto selectedManager = availableManagers.get(
        new Random().nextInt(availableManagers.size())
    );

    if (hubRepository.existsByHubName(requestDto.getHubName())) {
      throw new IllegalArgumentException("이미 존재하는 허브 이름입니다.");
    }
    if (hubRepository.existsByAddress(requestDto.getAddress())) {
      throw new IllegalArgumentException("이미 존재하는 주소입니다.");
    }

    Long currentId = Long.valueOf(userIdHeader);

    Hub hub = hubRepository.save(
        Hub.builder()
            .userId(selectedManager.getUserId())
            .hubName(requestDto.getHubName())
            .address(requestDto.getAddress())
            .latitude(requestDto.getLatitude())
            .longitude(requestDto.getLongitude())
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );

    kakaoMapApiService.autoCreateHubRoute(userIdHeader);

    return new HubCreateResponseDto(hub);
  }

  // 허브 단일 조회
  @Override
  public HubDetailResponseDto getHubDetail(UUID hubId) {
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));
    return HubDetailResponseDto.toResponse(hub);
  }

  // 허브 전체 조회
  @Override
  public Page<HubListResponseDto> getHubList(Pageable pageable) {
    Page<Hub> hubs = hubRepository.findAll(pageable);
    List<HubListResponseDto> dtoList = hubs.stream()
        .map(HubListResponseDto::toResponse)
        .collect(Collectors.toList());
    return PaginationUtils.paginateList(dtoList, pageable);
  }

  // 허브 검색 서비스
  @Override
  public Page<HubListResponseDto> getSearchHubs(String hubName, String address, UUID hubId,
      Pageable pageable) {
    if (hubId != null) {
      List<Hub> hubs = new ArrayList<>();
      hubRepository.findById(hubId).ifPresent(hubs::add);
      List<HubListResponseDto> dtoList = hubs.stream()
          .map(HubListResponseDto::toResponse)
          .collect(Collectors.toList());

      return PaginationUtils.paginateList(dtoList, pageable);

    } else {
      Page<Hub> hubPage = hubRepository.findByHubNameContainingOrAddressContaining(hubName, address,
          pageable);
      return hubPage.map(HubListResponseDto::toResponse);
    }

  }


  // 허브 수정
  @Override
  @Transactional
  public HubUpdateResponseDto updateHub(UUID hubId, HubUpdateRequestDto requestDto,
      String userIdHeader) {
    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    if (!Objects.equals(requestDto.getUserId(), hub.getUserId())) {
      if (hubRepository.existsByUserId(requestDto.getUserId())) {
        throw new IllegalArgumentException("이미 다른 허브에 관리자로 지정되어 있습니다");
      }
      hub.updateUserId(requestDto.getUserId());
    }

    if (!Objects.equals(requestDto.getHubName(), hub.getHubName())) {
      if (hubRepository.existsByHubName(requestDto.getHubName())) {
        throw new IllegalArgumentException("이미 존재하는 허브 이름입니다.");
      }
      hub.updateHubName(requestDto.getHubName());
    }

    if (!Objects.equals(requestDto.getAddress(), hub.getAddress())) {
      if (hubRepository.existsByAddress(requestDto.getAddress())) {
        throw new IllegalArgumentException("이미 존재하는 주소입니다.");
      }
      hub.updateAddress(requestDto.getAddress());
    }

    boolean isLocationUpdated = false;
    if (!Objects.equals(requestDto.getLatitude(), hub.getLatitude()) ||
        !Objects.equals(requestDto.getLongitude(), hub.getLongitude())) {
      hub.updateLatitude(requestDto.getLatitude());
      hub.updateLongitude(requestDto.getLongitude());
      isLocationUpdated = true;
    }

    Long currentId = Long.valueOf(userIdHeader);
    hub.updateUpdateBy(currentId);
    hub.updateLatitude(requestDto.getLatitude());
    hub.updateLongitude(requestDto.getLongitude());

    Hub updateHub = hubRepository.save(hub);

    if (isLocationUpdated) {
      hubRouteService.updateRoutesForHub(updateHub);
    }

    return new HubUpdateResponseDto(updateHub);
  }


  @Override
  @Transactional
  public void deleteHub(Long userId, UUID hubId, String userIdHeader) {


    hubRouteService.autoDeleteHubRoute(hubId, userIdHeader);

    Hub hub = hubRepository.findById(hubId)
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브 정보가 없습니다."));

    Long currentId = Long.valueOf(userIdHeader);

    hub.setDeletedBy(currentId);
    hub.setDeletedAt(LocalDateTime.now());
    hubRepository.save(hub);

  }
}
