package com.example.warehouse_service.facade;

import com.example.dto.AddProductRequestDto;

public interface WarehouseFacade {

    void processProducts(AddProductRequestDto dto);
}
