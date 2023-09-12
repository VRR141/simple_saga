package com.example.warehouse_service.facade.impl;

import com.example.dto.AddProductRequestDto;
import com.example.warehouse_service.facade.WarehouseFacade;
import com.example.warehouse_service.messaging.producer.KafkaProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WarehouseFacadeImpl implements WarehouseFacade {

    private final KafkaProcessor kafkaProcessor;

    @Override
    public void processProducts(AddProductRequestDto dto) {
        kafkaProcessor.sendProductToProductService(dto);
    }
}
