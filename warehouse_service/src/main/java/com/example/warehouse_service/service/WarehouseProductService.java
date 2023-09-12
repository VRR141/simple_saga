package com.example.warehouse_service.service;

import com.example.warehouse_service.domain.WarehouseProduct;

import java.util.Collection;
import java.util.UUID;

public interface WarehouseProductService {

    Collection<WarehouseProduct> addProductsToWarehouse(
            UUID warehouseUuid,
            Collection<WarehouseProduct> productsIdentifiers);
}
