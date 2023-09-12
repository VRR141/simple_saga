package com.example.warehouse_service.jpa;

import com.example.warehouse_service.domain.WarehouseProduct;
import com.example.warehouse_service.domain.WarehouseProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.UUID;

public interface WarehouseProductJpaRepository extends JpaRepository<WarehouseProduct, WarehouseProductId> {

    @Query("""
            from WarehouseProduct wp
            where
            wp.id.warehouse.id = :warehouseId
            and
            wp.id.productUuid in :productIdentifiers
            """)
    Collection<WarehouseProduct> findWarehouseProductById(Long warehouseId, Collection<UUID> productIdentifiers);
}
