package com.chill_guys.product_service.domain.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.UUID;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "p_product")
public class Product {
    @Generated
    private static final Logger log = LoggerFactory.getLogger(Product.class);
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private UUID id;
    private UUID companyId;
    private UUID hubId;
    private String name;
    private Long stock;
    private Timestamp createdAt;
    private Long createdBy;
    private Timestamp updatedAt;
    private Long updatedBy;
    private Timestamp deletedAt;
    private Long deletedBy;

    public boolean decreaseStock(Long quantity) {
        if (this.stock >= quantity) {
            this.stock = this.stock - quantity;
            log.info("가능");
            return true;
        } else {
            return false;
        }
    }

    public boolean increaseStock(Long quantity) {
        this.stock = this.stock + quantity;
        return true;
    }

    @Generated
    public static ProductBuilder builder() {
        return new ProductBuilder();
    }

    @Generated
    public UUID getId() {
        return this.id;
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
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }

    @Generated
    public Long getCreatedBy() {
        return this.createdBy;
    }

    @Generated
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }

    @Generated
    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    @Generated
    public Timestamp getDeletedAt() {
        return this.deletedAt;
    }

    @Generated
    public Long getDeletedBy() {
        return this.deletedBy;
    }

    @Generated
    public void setId(final UUID id) {
        this.id = id;
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
    public void setCreatedAt(final Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Generated
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    @Generated
    public void setUpdatedAt(final Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Generated
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Generated
    public void setDeletedAt(final Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Generated
    public void setDeletedBy(final Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Generated
    public Product() {
    }

    @Generated
    public Product(final UUID id, final UUID companyId, final UUID hubId, final String name, final Long stock, final Timestamp createdAt, final Long createdBy, final Timestamp updatedAt, final Long updatedBy, final Timestamp deletedAt, final Long deletedBy) {
        this.id = id;
        this.companyId = companyId;
        this.hubId = hubId;
        this.name = name;
        this.stock = stock;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.deletedAt = deletedAt;
        this.deletedBy = deletedBy;
    }

    @Generated
    public static class ProductBuilder {
        @Generated
        private UUID id;
        @Generated
        private UUID companyId;
        @Generated
        private UUID hubId;
        @Generated
        private String name;
        @Generated
        private Long stock;
        @Generated
        private Timestamp createdAt;
        @Generated
        private Long createdBy;
        @Generated
        private Timestamp updatedAt;
        @Generated
        private Long updatedBy;
        @Generated
        private Timestamp deletedAt;
        @Generated
        private Long deletedBy;

        @Generated
        ProductBuilder() {
        }

        @Generated
        public ProductBuilder id(final UUID id) {
            this.id = id;
            return this;
        }

        @Generated
        public ProductBuilder companyId(final UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        @Generated
        public ProductBuilder hubId(final UUID hubId) {
            this.hubId = hubId;
            return this;
        }

        @Generated
        public ProductBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @Generated
        public ProductBuilder stock(final Long stock) {
            this.stock = stock;
            return this;
        }

        @Generated
        public ProductBuilder createdAt(final Timestamp createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        @Generated
        public ProductBuilder createdBy(final Long createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        @Generated
        public ProductBuilder updatedAt(final Timestamp updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        @Generated
        public ProductBuilder updatedBy(final Long updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        @Generated
        public ProductBuilder deletedAt(final Timestamp deletedAt) {
            this.deletedAt = deletedAt;
            return this;
        }

        @Generated
        public ProductBuilder deletedBy(final Long deletedBy) {
            this.deletedBy = deletedBy;
            return this;
        }

        @Generated
        public Product build() {
            return new Product(this.id, this.companyId, this.hubId, this.name, this.stock, this.createdAt, this.createdBy, this.updatedAt, this.updatedBy, this.deletedAt, this.deletedBy);
        }

        @Generated
        public String toString() {
            String var10000 = String.valueOf(this.id);
            return "Product.ProductBuilder(id=" + var10000 + ", companyId=" + String.valueOf(this.companyId) + ", hubId=" + String.valueOf(this.hubId) + ", name=" + this.name + ", stock=" + this.stock + ", createdAt=" + String.valueOf(this.createdAt) + ", createdBy=" + this.createdBy + ", updatedAt=" + String.valueOf(this.updatedAt) + ", updatedBy=" + this.updatedBy + ", deletedAt=" + String.valueOf(this.deletedAt) + ", deletedBy=" + this.deletedBy + ")";
        }
    }
}