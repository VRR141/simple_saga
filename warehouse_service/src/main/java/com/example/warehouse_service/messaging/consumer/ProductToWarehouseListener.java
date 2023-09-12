package com.example.warehouse_service.messaging.consumer;

import com.example.dto.MessagingProductDto;
import com.example.warehouse_service.domain.WarehouseProduct;
import com.example.warehouse_service.domain.WarehouseProductId;
import com.example.warehouse_service.service.WarehouseProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductToWarehouseListener {

    private final WarehouseProductService warehouseProductService;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ProductToWarehouseTopic", groupId = "group_id")
    void listen(final String products) throws IOException {

        MessagingProductDto messagingProductDto
                = objectMapper.readValue(products, MessagingProductDto.class);

        log.info("Start processing {} with collection of products {} size",
                messagingProductDto.getClass().getSimpleName(),
                messagingProductDto.getProducts().size());

        System.out.println(messagingProductDto);

        Collection<WarehouseProduct> warehouseProducts = warehouseProductService.addProductsToWarehouse(
                messagingProductDto.getWarehouseIdentifier(),
                messagingProductDto.getProducts()
                        .stream()
                        .map(pr -> WarehouseProduct.builder()
                                .id(WarehouseProductId.builder()
                                        .productUuid(pr.getProductDto().getProductIdentifier())
                                        .build())
                                .amount(pr.getAmount())
                                .build())
                        .toList());

        log.info("Products successfully saved {}", warehouseProducts.size());
    }
}
