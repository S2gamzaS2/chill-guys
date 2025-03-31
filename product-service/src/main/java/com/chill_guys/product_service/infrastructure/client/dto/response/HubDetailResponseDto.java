package com.chill_guys.product_service.infrastructure.client.dto.response;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Generated;

public class HubDetailResponseDto {
    private UUID id;
    private Long userId;
    private String hubName;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Generated
    public static HubDetailResponseDtoBuilder builder() {
        return new HubDetailResponseDtoBuilder();
    }

    @Generated
    public UUID getId() {
        return this.id;
    }

    @Generated
    public Long getUserId() {
        return this.userId;
    }

    @Generated
    public String getHubName() {
        return this.hubName;
    }

    @Generated
    public String getAddress() {
        return this.address;
    }

    @Generated
    public BigDecimal getLatitude() {
        return this.latitude;
    }

    @Generated
    public BigDecimal getLongitude() {
        return this.longitude;
    }

    @Generated
    public HubDetailResponseDto() {
    }

    @Generated
    public HubDetailResponseDto(final UUID id, final Long userId, final String hubName, final String address, final BigDecimal latitude, final BigDecimal longitude) {
        this.id = id;
        this.userId = userId;
        this.hubName = hubName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Generated
    public static class HubDetailResponseDtoBuilder {
        @Generated
        private UUID id;
        @Generated
        private Long userId;
        @Generated
        private String hubName;
        @Generated
        private String address;
        @Generated
        private BigDecimal latitude;
        @Generated
        private BigDecimal longitude;

        @Generated
        HubDetailResponseDtoBuilder() {
        }

        @Generated
        public HubDetailResponseDtoBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        @Generated
        public HubDetailResponseDtoBuilder userId(final Long userId) {
            this.userId = userId;
            return this;
        }

        @Generated
        public HubDetailResponseDtoBuilder hubName(final String hubName) {
            this.hubName = hubName;
            return this;
        }

        @Generated
        public HubDetailResponseDtoBuilder address(final String address) {
            this.address = address;
            return this;
        }

        @Generated
        public HubDetailResponseDtoBuilder latitude(final BigDecimal latitude) {
            this.latitude = latitude;
            return this;
        }

        @Generated
        public HubDetailResponseDtoBuilder longitude(final BigDecimal longitude) {
            this.longitude = longitude;
            return this;
        }

        @Generated
        public HubDetailResponseDto build() {
            return new HubDetailResponseDto(this.id, this.userId, this.hubName, this.address, this.latitude, this.longitude);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.id);
            return "HubDetailResponseDto.HubDetailResponseDtoBuilder(id=" + var10000 + ", userId=" + this.userId + ", hubName=" + this.hubName + ", address=" + this.address + ", latitude=" + String.valueOf(this.latitude) + ", longitude=" + String.valueOf(this.longitude) + ")";
        }
    }
}