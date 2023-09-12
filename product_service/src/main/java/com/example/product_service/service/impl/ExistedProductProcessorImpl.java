package com.example.product_service.service.impl;

import com.example.dto.ExistedProduct;
import com.example.dto.ProductDto;
import com.example.product_service.domain.Product;
import com.example.product_service.jpa.ProductJpaRepository;
import com.example.product_service.service.ProductProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@RequiredArgsConstructor
public class ExistedProductProcessorImpl implements ProductProcessor<ExistedProduct> {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Class<ExistedProduct> appliesTo() {
        return ExistedProduct.class;
    }

    @Override
    public Collection<ExistedProduct> process(Collection<ExistedProduct> products) {
        Collection<Product> productsByProductIdentifier = productJpaRepository
                .findProductsByProductIdentifier(products.stream()
                        .map(ProductDto::getProductIdentifier)
                        .toList());
        if (productsByProductIdentifier.size() != products.size()) {
            throw new RuntimeException("Not enough products in db");
        }
        return productsByProductIdentifier.stream().map(p -> {
            ExistedProduct existedProduct = new ExistedProduct();
            existedProduct.setProductIdentifier(p.getProductIdentifier());
            return existedProduct;
        }).toList();
    }
}
