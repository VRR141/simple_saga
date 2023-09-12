package com.example.product_service.service.impl;

import com.example.dto.NewProductDto;
import com.example.product_service.domain.Product;
import com.example.product_service.jpa.ProductJpaRepository;
import com.example.product_service.service.ProductProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NewProductProcessorImpl implements ProductProcessor<NewProductDto> {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Class<NewProductDto> appliesTo() {
        return NewProductDto.class;
    }

    @Override
    public Collection<NewProductDto> process(Collection<NewProductDto> products) {
        List<Product> productsForSave = products.stream().map(np -> Product.builder()
                .productIdentifier(np.getProductIdentifier())
                .description(np.getDescription())
                .name(np.getName())
                .build()).toList();
        productJpaRepository.saveAll(productsForSave);
        return productsForSave.stream().map(p -> {
            NewProductDto newProductDto = new NewProductDto();
            newProductDto.setProductIdentifier(p.getProductIdentifier());
            return newProductDto;
        }).toList();
    }
}
