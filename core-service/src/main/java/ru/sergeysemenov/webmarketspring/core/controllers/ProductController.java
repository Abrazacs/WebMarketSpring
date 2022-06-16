package ru.sergeysemenov.webmarketspring.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.sergeysemenov.webmarketspring.api.*;
import ru.sergeysemenov.webmarketspring.core.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.core.entities.Product;
import ru.sergeysemenov.webmarketspring.core.exceptions.AppError;
import ru.sergeysemenov.webmarketspring.core.exceptions.ResourceNotFoundException;
import ru.sergeysemenov.webmarketspring.core.integrations.CartServiceIntegration;
import ru.sergeysemenov.webmarketspring.core.repositories.specifications.ProductSpecification;
import ru.sergeysemenov.webmarketspring.core.services.ProductService;


import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(
            summary = "Запрос на получение страницы с продуктами",
            responses ={
                    @ApiResponse(
                            description = "Возвращает выбранную страницу с заданным кол-вом " +
                                    "товаров на странице, отфильтрованную по заданным критериям. " +
                                    "По дефолту возвращает 1ую страницу с 10 товарами без фильтров",
                            content = @Content(schema = @Schema(implementation = PageDto.class)),
                            responseCode = "200"
                    )
            }
    )
    @GetMapping
    public PageDto getAllProducts(
            @RequestParam(name = "p", defaultValue = "1") @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "page_size", defaultValue = "10") @Parameter(description = "Кло-во товаров на странице") Integer pageSize,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Фильтр по наиенованию (можно часть)") String titlePart,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Фильтр мин. цена") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Фильтр мак. цена") Integer maxPrice) {

        return productService.findAll(page , pageSize, titlePart, minPrice, maxPrice);
    }

    @Operation(
            summary = "Запрос на получение продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id){
        return productConverter.entityToDto(productService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Продукт с id: " + id + " не найден")));
    }

    @Operation(
            summary = "Запрос на создание нового продукта",
            responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewProducts(@RequestBody ProductDto productDto) {
        productService.createNewProduct(productDto);
    }

    @Operation(
            summary = "Запрос на удаление продукта по id",
            responses = {
                    @ApiResponse(
                            description = "Продукт удален из БД", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable @Parameter(description = "Id продукта", required = true) Long id) {
        productService.deleteById(id);
    }

}

