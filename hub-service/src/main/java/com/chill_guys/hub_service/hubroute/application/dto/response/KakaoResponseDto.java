package com.chill_guys.hub_service.hubroute.application.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class KakaoResponseDto {

  private String transId;
  private List<Route> routes;

  @Getter
  public static class Route {
    private int resultCode;
    private String resultMsg;
    private Summary summary;
    private List<Section> sections;
  }

  @Getter
  public static class Summary {
    private double distance;
    private int duration;
  }

  @Getter
  public static class Section {
    private double distance;
    private int duration;
  }
}
