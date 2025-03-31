package com.chill_guys.hub_service.hub.application.dto.mock;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import com.chill_guys.hub_service.hub.domain.repository.HubRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DataLoader implements CommandLineRunner {

  private final HubRepository hubRepository;
  private final EntityManager entityManager;

  @Override
  @Transactional
  public void run(String... args) {
    String count = entityManager.createNativeQuery("select count(*) from p_hub").getSingleResult()
        .toString();
    log.info("DB 에 들은 데이터 갯수 : {}", count);
    if ("0".equals(count)) {

      entityManager.createNativeQuery("TRUNCATE TABLE p_hub RESTART IDENTITY CASCADE")
          .executeUpdate();

      Long userId = 1L;

      List<Hub> hubs = List.of(
          Hub.builder()
              .userId(userId)
              .hubName("서울특별시 센터")
              .address("서울특별시 송파구 송파대로 55")
              .latitude(new BigDecimal("37.4742027808565"))
              .longitude(new BigDecimal("127.123621185562"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("경기 북부 센터")
              .address("경기도 고양시 덕양구 권율대로 570")
              .latitude(new BigDecimal("37.6403771056018"))
              .longitude(new BigDecimal("126.87379545786"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("경기 남부 센터")
              .address("경기도 이천시 덕평로 257-21")
              .latitude(new BigDecimal("37.1896213142136"))
              .longitude(new BigDecimal("127.375050006958"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("부산광역시 센터")
              .address("부산 동구 중앙대로 206")
              .latitude(new BigDecimal("35.117605126596"))
              .longitude(new BigDecimal("129.045060216345"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("대구광역시 센터")
              .address("대구 북구 태평로 161")
              .latitude(new BigDecimal("35.8758849492106"))
              .longitude(new BigDecimal("128.596129208483"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("인천광역시 센터")
              .address("인천 남동구 정각로 29")
              .latitude(new BigDecimal("37.4560499608337"))
              .longitude(new BigDecimal("126.705255744089"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("광주광역시 센터")
              .address("광주 서구 내방로 111")
              .latitude(new BigDecimal("35.1600994105234"))
              .longitude(new BigDecimal("126.851461925213"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("대전광역시 센터")
              .address("대전 서구 둔산로 100")
              .latitude(new BigDecimal("36.3503849976553"))
              .longitude(new BigDecimal("127.384633005948"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("울산광역시 센터")
              .address("울산 남구 중앙로 201")
              .latitude(new BigDecimal("35.5390270962011"))
              .longitude(new BigDecimal("129.311356392207"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("세종특별자치시 센터")
              .address("세종특별자치시 한누리대로 2130")
              .latitude(new BigDecimal("36.4800579897497"))
              .longitude(new BigDecimal("127.289039408864"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("강원특별자치도 센터")
              .address("강원특별자치도 춘천시 중앙로 1")
              .latitude(new BigDecimal("37.8800729197963"))
              .longitude(new BigDecimal("127.727907820318"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("충청북도 센터")
              .address("충북 청주시 상당구 상당로 82")
              .latitude(new BigDecimal("36.6353867908159"))
              .longitude(new BigDecimal("127.491428436987"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("충청남도 센터")
              .address("충남 홍성군 홍북읍 충남대로 21")
              .latitude(new BigDecimal("36.6590416999343"))
              .longitude(new BigDecimal("126.673057036952"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("전북특별자치도 센터")
              .address("전북특별자치도 전주시 완산구 효자로 225")
              .latitude(new BigDecimal("35.8194621650578"))
              .longitude(new BigDecimal("127.106396942356"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("전라남도 센터")
              .address("전남 무안군 삼향읍 오룡길 1")
              .latitude(new BigDecimal("34.8174727676363"))
              .longitude(new BigDecimal("126.465415935304"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("경상북도 센터")
              .address("경북 안동시 풍천면 도청대로 455")
              .latitude(new BigDecimal("36.5761205474728"))
              .longitude(new BigDecimal("128.505722686385"))
              .createdBy(userId)
              .updatedBy(userId)
              .build(),
          Hub.builder()
              .userId(userId)
              .hubName("경상남도 센터")
              .address("경남 창원시 의창구 중앙대로 300")
              .latitude(new BigDecimal("35.2378032514675"))
              .longitude(new BigDecimal("128.691940442146"))
              .createdBy(userId)
              .updatedBy(userId)
              .build()
      );
      hubRepository.saveAll(hubs);
    }
  }

}


