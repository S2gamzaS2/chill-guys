package com.chill_guys.product_service.application.dto.request;

import java.util.UUID;
import lombok.Generated;

public class ProductCreateRequestDto {
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;

    @Generated
    public static ProductCreateRequestDtoBuilder builder() {
        return new ProductCreateRequestDtoBuilder();
    }

    @Generated
    public UUID getCompanyId() {
        return this.companyId;
    }

    @Generated
    public UUID getHubId() {
        return this.hubId;
    }

    @Generated
    public String getName() {
        return this.name;
    }

    @Generated
    public Long getStock() {
        return this.stock;
    }

    @Generated
    public void setCompanyId(final UUID companyId) {
        this.companyId = companyId;
    }

    @Generated
    public void setHubId(final UUID hubId) {
        this.hubId = hubId;
    }

    @Generated
    public void setName(final String name) {
        this.name = name;
    }

    @Generated
    public void setStock(final Long stock) {
        this.stock = stock;
    }

    @Generated
    public ProductCreateRequestDto() {
    }

    @Generated
    public ProductCreateRequestDto(final UUID companyId, final UUID hubId, final String name, final Long stock) {
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.stock = stock;
    }

    @Generated
    public static class ProductCreateRequestDtoBuilder {
        @Generated
        private UUID companyId;
        @Generated
        private UUID hubId;
        @Generated
        private String name;
        @Generated
        private Long stock;

        @Generated
        ProductCreateRequestDtoBuilder() {
        }

        @Generated
        public ProductCreateRequestDtoBuilder companyId(final UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        @Generated
        public ProductCreateRequestDtoBuilder hubId(final UUID hubId) {
            this.hubId = hubId;
            return this;
        }

        @Generated
        public ProductCreateRequestDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public ProductCreateRequestDtoBuilder stock(final Long stock) {
            this.stock = stock;
            return this;
        }

        @Generated
        public ProductCreateRequestDto build() {
            return new ProductCreateRequestDto(this.companyId, this.hubId, this.name, this.stock);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.companyId);
            return "ProductCreateRequestDto.ProductCreateRequestDtoBuilder(companyId=" + var10000 + ", hubId=" + String.valueOf(this.hubId) + ", name=" + this.name + ", stock=" + this.stock + ")";
        }
    }
}
