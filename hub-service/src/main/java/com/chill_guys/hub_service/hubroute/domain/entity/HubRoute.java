package com.chill_guys.hub_service.hubroute.domain.entity;

import com.chill_guys.hub_service.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@SQLRestriction("deleted_at IS NULL")
@Table(name = "p_hub_route")
public class HubRoute extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "start_hub_id", nullable = false)
  private UUID startHubId;

  @Column(name = "end_hub_id", nullable = false)
  private UUID endHubId;

  @Column(name = "start_hub_name", nullable = false)
  private String startHubName;

  @Column(name = "end_hub_name", nullable = false)
  private String endHubName;

  @Column(name = "delivery_time", nullable = false)
  private Integer deliveryTime;

  @Column(name = "delivery_distance", nullable = false)
  private Double deliveryDistance;

  public void updateDeliveryTime(Integer deliveryTime) {
    this.deliveryTime = deliveryTime;
  }
  public void updateDeliveryDistance(Double deliveryDistance) {
    this.deliveryDistance = deliveryDistance;
  }
  public void updateStartHubName(String startHubName) {
    this.startHubName = startHubName;
  }
  public void updateEndHubName(String endHubName) {
    this.endHubName = endHubName;
  }
}
