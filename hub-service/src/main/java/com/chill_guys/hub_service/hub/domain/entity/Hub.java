package com.chill_guys.hub_service.hub.domain.entity;


import com.chill_guys.hub_service.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
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
@Table(name = "p_hub")
@SQLRestriction("deleted_at IS NULL")
public class Hub extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "user_id", nullable = false)
  private long userId;

  @Column(name = "hub_name", nullable = false)
  private String hubName;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "latitude", nullable = false)
  private BigDecimal latitude;

  @Column(name = "longitude", nullable = false)
  private BigDecimal longitude;

  public void updateUserId(long userId) {
    this.userId = userId;
  }

  public void updateHubName(String hubName) {
    this.hubName = hubName;
  }

  public void updateAddress(String address) {
    this.address = address;
  }

  public void updateLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public void updateLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }


}
