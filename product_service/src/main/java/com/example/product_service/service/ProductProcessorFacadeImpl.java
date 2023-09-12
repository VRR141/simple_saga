package com.example.product_service.service;

import com.example.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductProcessorFacadeImpl implements ProductProcessorFacade {

    private final List<ProductProcessor> processors;

    private Map<Class, ProductProcessor> processorsMap;

    @PostConstruct
    void pc() {
        processorsMap = processors.stream().collect(Collectors.toMap(
                ProductProcessor::appliesTo,
                Function.identity()
        ));
    }

    @Override
    public Collection<ProductDto> process(Collection<ProductDto> product) {
        Map<Class<? extends ProductDto>, Collection<ProductDto>> executorMap = new HashMap<>();
        product.forEach(p -> {
            Class<? extends ProductDto> targetClass = p.getClass();
            Collection<ProductDto> products = executorMap.get(p.getClass());
            if (products == null) {
                executorMap.put(targetClass, new ArrayList<>());
            }
            executorMap.get(targetClass).add(p);
        });
        List<ProductDto> result = new ArrayList<>();
        executorMap.forEach((k, v) -> result.addAll(processorsMap.get(k).process(v)));
        return result;
    }
}
