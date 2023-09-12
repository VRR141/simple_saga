package com.example.product_service.facade;

import com.example.dto.MessagingProductDto;
import com.example.dto.ProductToWarehouseDto;
import com.example.product_service.messaging.producer.ProductToWarehouseProducerService;
import com.example.product_service.service.ProductProcessorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductProcessorFacade productProcessorFacade;

    public MessagingProductDto process(MessagingProductDto messagingProductDto) {
        try {
            productProcessorFacade.process(messagingProductDto.getProducts()
                    .stream()
                    .map(ProductToWarehouseDto::getProductDto)
                    .toList());
            messagingProductDto.setProductServiceAcceptance(true);
        } catch (RuntimeException ex){
            messagingProductDto.setProductServiceAcceptance(false);
        }
        return messagingProductDto;
    }
}
