package com.chill_guys.hub_service.hub.presentation.controller;

import com.chill_guys.hub_service.global.dto.ResponseDto;
import com.chill_guys.hub_service.global.utils.PageableUtils;
import com.chill_guys.hub_service.hub.application.dto.response.HubDetailResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubListResponseDto;
import com.chill_guys.hub_service.hub.application.service.HubService;
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

@Slf4j
@RestController
@RequestMapping("/api/hubs")
@RequiredArgsConstructor
public class HubController {

  private final HubService hubService;

  // 허브 단일 조회
  @GetMapping("/{hubId}")
  public ResponseEntity<HubDetailResponseDto> getHubDetail(@PathVariable UUID hubId) {
    HubDetailResponseDto responseDto = hubService.getHubDetail(hubId);
    return ResponseEntity.ok(responseDto);
  }

  // 허브 조회 및 검색
  @GetMapping
  public ResponseEntity<ResponseDto<Page<HubListResponseDto>>> getHubList(
      @RequestParam(required = false) String hubName,
      @RequestParam(required = false) String address,
      @RequestParam(required = false) UUID hubId,
      @PageableDefault(
          size = 10,
          page = 1,
          sort = {"createdAt", "updatedAt"},
          direction = Direction.ASC
      ) Pageable pageable) {

    Pageable validatedPageable = PageableUtils.validatePageable(pageable);

    Page<HubListResponseDto> result;

    if (hubName == null && address == null && hubId == null) {
      result = hubService.getHubList(validatedPageable);
    } else {
      result = hubService.getSearchHubs(hubName, address, hubId, validatedPageable);
    }
    return ResponseEntity.ok(ResponseDto.success(result));
  }

}
