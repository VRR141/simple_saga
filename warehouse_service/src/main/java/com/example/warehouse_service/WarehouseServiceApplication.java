package com.example.warehouse_service;

import com.example.dto.AddProductRequestDto;
import com.example.dto.MessagingProductDto;
import com.example.dto.NewProductDto;
import com.example.dto.ProductToWarehouseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class WarehouseServiceApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        AddProductRequestDto dto = new AddProductRequestDto();
        dto.setWarehouseIdentifier(UUID.randomUUID());
        Random random = new Random();
        List<ProductToWarehouseDto> list = Stream.generate(() -> {
            ProductToWarehouseDto pDto = new ProductToWarehouseDto();
            NewProductDto newProductDto = new NewProductDto();
            newProductDto.setProductIdentifier(UUID.randomUUID());
            newProductDto.setName("asd" + random.nextInt(25));
            newProductDto.setDescription("desc" + random.nextInt(33));
            pDto.setProductDto(newProductDto);
            pDto.setAmount(random.nextInt(25) + 5);
            return pDto;
        }).limit(25).toList();
        dto.setProducts(list);
        String s = new ObjectMapper().writeValueAsString(dto);
        System.out.println(s);
    }

    public static void main(String[] args) {
        SpringApplication.run(WarehouseServiceApplication.class, args);
    }

}
