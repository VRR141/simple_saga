package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddProductRequestDto {

    private Collection<ProductToWarehouseDto> products;

    private UUID warehouseIdentifier;
}
