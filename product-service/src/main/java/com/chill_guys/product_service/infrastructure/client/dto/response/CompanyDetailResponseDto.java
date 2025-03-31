package com.chill_guys.product_service.infrastructure.client.dto.response;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Generated;

public class CompanyDetailResponseDto {
    private UUID id;
    private UUID hubId;
    private String name;
    private String type;
    private String address;
    private String phone;
    private Long createdBy;
    private Timestamp createdAt;
    private Long updatedBy;
    private Timestamp updatedAt;
    private Long deletedBy;
    private Timestamp deletedAt;

    @Generated
    public static CompanyDetailResponseDtoBuilder builder() {
        return new CompanyDetailResponseDtoBuilder();
    }

    @Generated
    public UUID getId() {
        return this.id;
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
    public String getType() {
        return this.type;
    }

    @Generated
    public String getAddress() {
        return this.address;
    }

    @Generated
    public String getPhone() {
        return this.phone;
    }

    @Generated
    public Long getCreatedBy() {
        return this.createdBy;
    }

    @Generated
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    @Generated
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    @Generated
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    @Generated
    public Long getDeletedBy() {
        return this.deletedBy;
    }

    @Generated
    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    @Generated
    public CompanyDetailResponseDto() {
    }

    @Generated
    public CompanyDetailResponseDto(final UUID id, final UUID hubId, final String name, final String type, final String address, final String phone, final Long createdBy, final Timestamp createdAt, final Long updatedBy, final Timestamp updatedAt, final Long deletedBy, final Timestamp deletedAt) {
        this.id = id;
        this.hubId = hubId;
        this.name = name;
        this.type = type;
        this.address = address;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.deletedBy = deletedBy;
        this.deletedAt = deletedAt;
    }

    @Generated
    public static class CompanyDetailResponseDtoBuilder {
        @Generated
        private UUID id;
        @Generated
        private UUID hubId;
        @Generated
        private String name;
        @Generated
        private String type;
        @Generated
        private String address;
        @Generated
        private String phone;
        @Generated
        private Long createdBy;
        @Generated
        private Timestamp createdAt;
        @Generated
        private Long updatedBy;
        @Generated
        private Timestamp updatedAt;
        @Generated
        private Long deletedBy;
        @Generated
        private Timestamp deletedAt;

        @Generated
        CompanyDetailResponseDtoBuilder() {
        }

        @Generated
        public CompanyDetailResponseDtoBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder hubId(final UUID hubId) {
            this.hubId = hubId;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder type(final String type) {
            this.type = type;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder address(final String address) {
            this.address = address;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder phone(final String phone) {
            this.phone = phone;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder createdBy(final Long createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder createdAt(final Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder updatedBy(final Long updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder updatedAt(final Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder deletedBy(final Long deletedBy) {
            this.deletedBy = deletedBy;
            return this;
        }

        @Generated
        public CompanyDetailResponseDtoBuilder deletedAt(final Timestamp deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        @Generated
        public CompanyDetailResponseDto build() {
            return new CompanyDetailResponseDto(this.id, this.hubId, this.name, this.type, this.address, this.phone, this.createdBy, this.createdAt, this.updatedBy, this.updatedAt, this.deletedBy, this.deletedAt);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.id);
            return "CompanyDetailResponseDto.CompanyDetailResponseDtoBuilder(id=" + var10000 + ", hubId=" + String.valueOf(this.hubId) + ", name=" + this.name + ", type=" + this.type + ", address=" + this.address + ", phone=" + this.phone + ", createdBy=" + this.createdBy + ", createdAt=" + String.valueOf(this.createdAt) + ", updatedBy=" + this.updatedBy + ", updatedAt=" + String.valueOf(this.updatedAt) + ", deletedBy=" + this.deletedBy + ", deletedAt=" + String.valueOf(this.deletedAt) + ")";
        }
    }
}
