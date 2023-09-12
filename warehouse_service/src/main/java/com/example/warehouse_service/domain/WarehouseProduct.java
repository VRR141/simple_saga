package com.example.warehouse_service.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "warehouse_product", schema = "warehouse")
public class WarehouseProduct {

    @EmbeddedId
    private WarehouseProductId id;

    private Integer amount;
}
