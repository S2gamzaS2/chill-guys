package com.chill_guys.hub_service.hubroute.presentation.controller;

import com.chill_guys.hub_service.global.dto.ResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.request.HubRouteCreateRequestDto;
import com.chill_guys.hub_service.hubroute.application.dto.request.HubRouteUpdateRequestDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteCreateResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteUpdateResponseDto;
import com.chill_guys.hub_service.hubroute.application.service.HubRouteService;
import com.chill_guys.hub_service.hubroute.application.service.KakaoMapApiServiceImpl;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/master/hub-routes")
@RequiredArgsConstructor
@Slf4j
public class MasterHubRouteController {

  private final KakaoMapApiServiceImpl kakaoMapApiService;
  private final HubRouteService hubRouteService;

  // 허브 루트 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubRouteCreateResponseDto>> createHubRoute(
      @RequestBody HubRouteCreateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubRouteCreateResponseDto responseDto = kakaoMapApiService.createHubRoute(requestDto,
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 자동 생성
  @PostMapping("/auto")
  public ResponseEntity<ResponseDto<List<HubRouteCreateResponseDto>>> autoCreateHubRoute(
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    List<HubRouteCreateResponseDto> responseDto = kakaoMapApiService.autoCreateHubRoute(
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 수정
  @PutMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteUpdateResponseDto>> updateHubRoute(
      @PathVariable UUID hubRoutesId,
      @RequestBody HubRouteUpdateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubRouteUpdateResponseDto responseDto = hubRouteService.updateHubRoute(hubRoutesId, requestDto,
        userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 삭제
  @DeleteMapping("/{hubRoutesId}")
  public ResponseEntity<?> deleteHubRoute(Long userId, @PathVariable UUID hubRoutesId,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    hubRouteService.deleteHubRoute(userId, hubRoutesId, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
