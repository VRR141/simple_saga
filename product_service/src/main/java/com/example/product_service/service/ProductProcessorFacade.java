package com.example.product_service.service;

import com.example.dto.ProductDto;

import java.util.Collection;

public interface ProductProcessorFacade {

    Collection<ProductDto> process(Collection<ProductDto> product);
}
