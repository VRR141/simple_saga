package com.example.warehouse_service.rest;

import com.example.dto.AddProductRequestDto;
import com.example.warehouse_service.facade.WarehouseFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/warehouse")
@Slf4j
public class WarehouseController {

    private final WarehouseFacade warehouseFacade;

    @PostMapping("/products")
    ResponseEntity<Void> addProductToWarehouse(@RequestBody AddProductRequestDto dto){
        log.info("Accepted request for add product to warehouse {}", dto.getWarehouseIdentifier());
        warehouseFacade.processProducts(dto);
        log.info("Request successfully created");
        return ResponseEntity.status(201).build();
    }
}
