package com.example.warehouse_service.messaging.producer;

import com.example.dto.AddProductRequestDto;
import com.example.dto.MessagingProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProcessor {

    private final KafkaTemplate<UUID, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendProductToProductService(AddProductRequestDto dto){
        MessagingProductDto messagingProductDto
                = MessagingProductDto.builder()
                .products(dto.getProducts())
                .warehouseIdentifier(dto.getWarehouseIdentifier())
                .productServiceAcceptance(false)
                .build();
        try {
           String productBytes = objectMapper.writeValueAsString(messagingProductDto);
            kafkaTemplate.send(
                    "WarehouseToProductTopic",
                    messagingProductDto.getWarehouseIdentifier(),
                    productBytes);
            log.info("{} successfully sent to topic {}",
                    messagingProductDto.getClass().getSimpleName(),
                    "WarehouseToProductTopic");
        } catch (JsonProcessingException e) {
            log.error("Error occurred json serialise");
            throw new RuntimeException(e);
        }
    }
}
