package com.chill_guys.hub_service.hubroute.presentation.controller;

import com.chill_guys.hub_service.global.dto.ResponseDto;
import com.chill_guys.hub_service.global.utils.PageableUtils;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteDetailResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.HubRouteListResponseDto;
import com.chill_guys.hub_service.hubroute.application.service.HubRouteService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hub-routes")
@RequiredArgsConstructor
@Slf4j
public class HubRouteController {

  private final HubRouteService hubRouteService;

  // 허브 루트 단일 조회
  @GetMapping("/{hubRoutesId}")
  public ResponseEntity<ResponseDto<HubRouteDetailResponseDto>> getHubRouteDetail(
      @PathVariable UUID hubRoutesId) {
    HubRouteDetailResponseDto responseDto = hubRouteService.getHubRouteDetail(hubRoutesId);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 루트 조회 및 검색
  @GetMapping
  public ResponseEntity<List<HubRouteListResponseDto>> getHubRouteList(
      @RequestParam(required = false) UUID startHubId,
      @RequestParam(required = false) UUID endHubId,
      @PageableDefault(
          size = 10,
          page = 1,
          sort = {"createdAt", "updatedAt"},
          direction = Direction.ASC
      ) Pageable pageable) {

    Pageable validatedPageable = PageableUtils.validatePageable(pageable);

    Page<HubRouteListResponseDto> responseDto = hubRouteService.getHubRouteList(startHubId,
        endHubId, validatedPageable);
    List<HubRouteListResponseDto> listResponseDto = responseDto.getContent();
    return ResponseEntity.ok().body(listResponseDto);
  }


}
