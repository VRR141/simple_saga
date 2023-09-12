package com.example.product_service.messaging.consumer;

import com.example.dto.MessagingProductDto;
import com.example.product_service.facade.ProductFacade;
import com.example.product_service.messaging.producer.ProductToWarehouseProducerService;
import com.example.product_service.service.ProductProcessorFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductServiceListener {

    private final ProductFacade productFacade;

    private final ObjectMapper objectMapper;

    private final ProductToWarehouseProducerService kafkaService;

    @KafkaListener(topics = "WarehouseToProductTopic", groupId = "product_service")
    void listen(final String message) throws IOException {
        MessagingProductDto messagingProductDto
                = objectMapper.readValue(message, MessagingProductDto.class);
        log.info("Accepted message for process products");
        MessagingProductDto messagingDto = productFacade.process(messagingProductDto);
        log.info("products successfully processed");
        kafkaService.sendMessage(messagingDto);
    }
}
