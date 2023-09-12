package com.example.warehouse_service.jpa;

import com.example.warehouse_service.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<Warehouse, Long> {

    Optional<Warehouse> findWarehouseByWarehouseIdentifier(UUID identifier);
}
