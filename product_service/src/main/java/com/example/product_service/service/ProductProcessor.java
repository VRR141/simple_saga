package com.example.product_service.service;

import com.example.dto.ProductDto;

import java.util.Collection;

public interface ProductProcessor<P extends ProductDto> {

    Class<P> appliesTo();

    Collection<P> process(Collection<P> products);
}
