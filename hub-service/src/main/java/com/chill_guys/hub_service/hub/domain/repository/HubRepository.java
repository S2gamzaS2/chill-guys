package com.chill_guys.hub_service.hub.domain.repository;

import com.chill_guys.hub_service.hub.domain.entity.Hub;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HubRepository extends JpaRepository<Hub, UUID> {

  boolean existsByUserId(Long userId);

  boolean existsByHubName(String hubName);

  boolean existsByAddress(String address);

  Page<Hub> findByHubNameContainingOrAddressContaining(String hubName, String address,
      Pageable pageable);

  @Query("SELECT h FROM Hub h WHERE h.id <> :hubId")
  List<Hub> findAllExcept(@Param("hubId") UUID hubId);
}
