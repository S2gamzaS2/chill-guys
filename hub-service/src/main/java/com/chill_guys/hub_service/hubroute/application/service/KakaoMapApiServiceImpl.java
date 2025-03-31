package com.chill_guys.hub_service.hubroute.application.service;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import com.chill_guys.hub_service.hub.domain.repository.HubRepository;
import com.chill_guys.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.chill_guys.hub_service.hubroute.domain.entity.HubRoute;
import com.chill_guys.hub_service.hubroute.domain.repository.HubRouteRepository;
import com.chill_guys.hub_service.hubroute.infrastructure.client.KakaoClient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class KakaoMapApiServiceImpl implements KakaoMapService {

  private final HubRepository hubRepository;
  private final HubRouteRepository hubRouteRepository;
  private final KakaoClient kakaoClient;


  @Override
  @Transactional
  public HubRouteCreateResponseDto createHubRoute(HubRouteCreateRequestDto requestDto,
      String userIdHeader) {

    Hub startHub = hubRepository.findById(requestDto.getStartHubId())
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));
    Hub endHub = hubRepository.findById(requestDto.getEndHubId())
        .orElseThrow(() -> new IllegalArgumentException("해당하는 허브가 없습니다."));

    if (hubRouteRepository.existsByStartHubId(requestDto.getStartHubId())
        && hubRouteRepository.existsByEndHubId(
        requestDto.getEndHubId())) {
      throw new IllegalArgumentException("이미 등록된 경로 입니다.");
    }

    KakaoClient.RouteInfo routeInfo = kakaoClient.getRouteInfo(
        startHub.getLongitude(), startHub.getLatitude(),
        endHub.getLongitude(), endHub.getLatitude()
    );

    Long currentId = Long.valueOf(userIdHeader);

    HubRoute hubRoute = hubRouteRepository.save(
        HubRoute.builder()
            .startHubId(requestDto.getStartHubId())
            .endHubId(requestDto.getEndHubId())
            .startHubName(startHub.getHubName())
            .endHubName(endHub.getHubName())
            .deliveryTime(routeInfo.getTime())
            .deliveryDistance(routeInfo.getDistance())
            .createdBy(currentId)
            .updatedBy(currentId)
            .build()
    );
    return new HubRouteCreateResponseDto(hubRoute);
  }

  @Override
  @Transactional
  public List<HubRouteCreateResponseDto> autoCreateHubRoute(String userIdHeader) {
    log.info("허브 루트 자동 생성 시작");

    List<Hub> hubs = hubRepository.findAll();

    if (hubs.size() < 2) {
      throw new IllegalStateException("최소 2개 이상의 허브가 있어야 합니다.");
    }

    List<HubRoute> routes = new ArrayList<>();

    for (Hub startHub : hubs) {
      for (Hub endHub : hubs) {
        if (!startHub.getId().equals(endHub.getId())) {

          if (hubRouteRepository.existsByStartHubIdAndEndHubId(startHub.getId(), endHub.getId())) {
            log.info("이미 존재하는 루트: {} -> {}", startHub.getHubName(), endHub.getHubName());
            continue;
          }

          KakaoClient.RouteInfo routeInfo = kakaoClient.getRouteInfo(
              startHub.getLongitude(), startHub.getLatitude(),
              endHub.getLongitude(), endHub.getLatitude()
          );

          Long currentId = Long.valueOf(userIdHeader);

          HubRoute route = HubRoute.builder()
              .startHubId(startHub.getId())
              .endHubId(endHub.getId())
              .startHubName(startHub.getHubName())
              .endHubName(endHub.getHubName())
              .deliveryTime(routeInfo.getTime())
              .deliveryDistance(routeInfo.getDistance())
              .createdBy(currentId)
              .updatedBy(currentId)
              .build();

          routes.add(route);
        }
      }
    }
    hubRouteRepository.saveAll(routes);
    log.info("허브 루트 자동 생성 완료 - 생성된 루트 수: {}", routes.size());

    return routes.stream()
        .map(HubRouteCreateResponseDto::new)
        .collect(Collectors.toList());
  }

}