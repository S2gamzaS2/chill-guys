package com.chill_guys.hub_service.hub.presentation.controller;

import com.chill_guys.hub_service.global.dto.ResponseDto;
import com.chill_guys.hub_service.hub.application.dto.request.HubCreateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.request.HubUpdateRequestDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubCreateResponseDto;
import com.chill_guys.hub_service.hub.application.dto.response.HubUpdateResponseDto;
import com.chill_guys.hub_service.hub.application.service.HubService;
import jakarta.validation.Valid;
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

@Slf4j
@RestController
@RequestMapping("/api/master/hubs")
@RequiredArgsConstructor
public class MasterHubController {

  private final HubService hubService;

  // 허브 생성
  @PostMapping
  public ResponseEntity<ResponseDto<HubCreateResponseDto>> createHub(
      @Valid @RequestBody HubCreateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubCreateResponseDto responseDto = hubService.createHub(requestDto, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 수정
  @PutMapping("/{hubId}")
  public ResponseEntity<ResponseDto<HubUpdateResponseDto>> updateHub(@PathVariable UUID hubId,
      @Valid @RequestBody HubUpdateRequestDto requestDto,
      @RequestHeader(value = "X-User-Id") String userIdHeader) {
    HubUpdateResponseDto responseDto = hubService.updateHub(hubId, requestDto, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success(responseDto));
  }

  // 허브 삭제
  @DeleteMapping("/{hubId}")
  public ResponseEntity<?> deleteHub(Long userId, @PathVariable UUID hubId,
      @RequestHeader(value = "X-User-Id") String userIdHeader
  ) {
    hubService.deleteHub(userId, hubId, userIdHeader);
    return ResponseEntity.ok(ResponseDto.success("delete success"));
  }
}
