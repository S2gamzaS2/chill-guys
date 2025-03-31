package com.chill_guys.hub_service.hubroute.domain.repository;

import com.chill_guys.hub_service.hubroute.domain.entity.HubRoute;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HubRouteRepository extends JpaRepository<HubRoute, UUID> {

  boolean existsByStartHubId(UUID startHubId);

  boolean existsByEndHubId(UUID endHubId);

  List<HubRoute> findByStartHubIdOrEndHubId(UUID startHubId, UUID endHubId);

  List<HubRoute> findByStartHubIdAndEndHubId(UUID startHubId, UUID endHubId);

  Optional<HubRoute> findOptionalByStartHubIdAndEndHubId(UUID startHubId, UUID endHubId);

  boolean existsByStartHubIdAndEndHubId(UUID startHubId, UUID endHubId);

  List<HubRoute> findAllByStartHubIdOrEndHubId(UUID startHubId, UUID endHubId);
}
