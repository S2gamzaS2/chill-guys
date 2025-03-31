package com.chill_guys.hub_service.hubroute.infrastructure.client;

import com.chill_guys.hub_service.hubroute.application.dto.response.KakaoResponseDto;
import com.chill_guys.hub_service.hubroute.application.dto.response.KakaoResponseDto.Route;
import com.chill_guys.hub_service.hubroute.application.dto.response.KakaoResponseDto.Section;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class KakaoClient {

  private final RestTemplate restTemplate;

  @Value("${kakao.rest.api.key}")
  private String apiKey;

  public RouteInfo getRouteInfo(BigDecimal startLng, BigDecimal startLat, BigDecimal endLng,
      BigDecimal endLat) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Authorization", "KakaoAK " + apiKey);

    String startLocation = startLng + "," + startLat;
    String endLocation = endLng + "," + endLat;

    String encodeOrigin = URLEncoder.encode(startLocation, StandardCharsets.UTF_8);
    String encodeDestination = URLEncoder.encode(endLocation, StandardCharsets.UTF_8);
    String rawURI = "https://apis-navi.kakaomobility.com/v1/directions?origin="
        + encodeOrigin + "&destination=" + encodeDestination + "&summary=true";

    HttpEntity<String> entity = new HttpEntity<>(headers);
    URI uri = URI.create(rawURI);

    ResponseEntity<KakaoResponseDto> response = restTemplate.exchange(
        uri, HttpMethod.GET, entity, KakaoResponseDto.class
    );

    if (response.getBody() != null &&
        response.getBody().getRoutes() != null &&
        !response.getBody().getRoutes().isEmpty()) {

      Route route = response.getBody().getRoutes().get(0);
      if (route.getSections() != null && !route.getSections().isEmpty()) {
        Section section = route.getSections().get(0);
        double distance = section.getDistance();
        int time = section.getDuration() / 60;

        log.info("API 호출 결과 - Distance: {}, Time: {}", distance, time);
        return new RouteInfo(time, distance);
      }
    }

    throw new IllegalStateException("카카오 경로 API 응답이 유효하지 않습니다.");
  }

  @Getter
  @AllArgsConstructor
  public static class RouteInfo {

    private Integer time;
    private Double distance;
  }
}
