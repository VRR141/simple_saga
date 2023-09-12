package com.example.product_service.messaging.producer;

import com.example.dto.MessagingProductDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductToWarehouseProducerService {

    private final KafkaTemplate<UUID, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    public void sendMessage(MessagingProductDto dto){
        try {
            String bytes = objectMapper.writeValueAsString(dto);
            log.info("Accepted request for send message to ProductToWarehouseTopic");
            kafkaTemplate.send("ProductToWarehouseTopic", dto.getWarehouseIdentifier(), bytes);
            log.info("Message successfully send to ProductToWarehouseTopic");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
