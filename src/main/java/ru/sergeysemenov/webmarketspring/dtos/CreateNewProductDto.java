package ru.sergeysemenov.webmarketspring.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateNewProductDto {
    private String title;
    private BigDecimal price;
}
