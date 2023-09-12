package com.example.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewProductDto.class, name = "new"),
        @JsonSubTypes.Type(value = ExistedProduct.class, name = "existed")}
)
@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class ProductDto {

    private UUID productIdentifier;
}
