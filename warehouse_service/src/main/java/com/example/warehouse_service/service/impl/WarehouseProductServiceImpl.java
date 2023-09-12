package com.example.warehouse_service.service.impl;

import com.example.warehouse_service.domain.Warehouse;
import com.example.warehouse_service.domain.WarehouseProduct;
import com.example.warehouse_service.jpa.WarehouseJpaRepository;
import com.example.warehouse_service.jpa.WarehouseProductJpaRepository;
import com.example.warehouse_service.service.WarehouseProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class WarehouseProductServiceImpl implements WarehouseProductService {

    private final WarehouseProductJpaRepository warehouseProductJpaRepository;

    private final WarehouseJpaRepository warehouseJpaRepository;

    @Override
    @Transactional
    public Collection<WarehouseProduct> addProductsToWarehouse(UUID warehouseUuid,
                                                               Collection<WarehouseProduct> productsIdentifiers) {
        Warehouse warehouse =
                warehouseJpaRepository
                        .findWarehouseByWarehouseIdentifier(warehouseUuid)
                        .orElseThrow();
        Collection<WarehouseProduct> warehouseProductById
                = warehouseProductJpaRepository.findWarehouseProductById(
                warehouse.getId(),
                productsIdentifiers.stream()
                        .map(s -> s.getId().getProductUuid())
                        .toList());
        System.out.println(warehouseProductById);
        Map<UUID, WarehouseProduct> existedProducts = warehouseProductById.stream()
                .collect(Collectors.toMap(
                        v -> v.getId().getProductUuid(),
                        Function.identity()));

        Collection<WarehouseProduct> products = productsIdentifiers.stream()
                .peek((wp) -> {
                    WarehouseProduct existedWp = existedProducts.get(wp.getId().getProductUuid());
                    if (existedWp != null) {
                        existedWp.setAmount(existedWp.getAmount() + wp.getAmount());
                    } else {
                        wp.getId().setWarehouse(warehouse);
                    }
                })
                .filter((wp) -> existedProducts.get(wp.getId().getProductUuid()) == null)
                .toList();
        List<WarehouseProduct> savedProducts
                = warehouseProductJpaRepository.saveAll(products);
        return Stream.of(savedProducts, existedProducts.values())
                .flatMap(Collection::stream)
                .toList();
    }
}
