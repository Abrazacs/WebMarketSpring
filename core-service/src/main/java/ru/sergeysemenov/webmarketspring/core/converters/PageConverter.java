package ru.sergeysemenov.webmarketspring.core.converters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.sergeysemenov.webmarketspring.api.PageDto;
import ru.sergeysemenov.webmarketspring.api.ProductDto;
import ru.sergeysemenov.webmarketspring.core.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
@RequiredArgsConstructor
public class PageConverter {
    private final ProductConverter converter;

    public PageDto entityToDto(Page<Product> page) {
        PageDto pageDto = new PageDto();
        pageDto.setCurrentPage(page.getNumber());
        pageDto.setTotalPages(page.getTotalPages());
        pageDto.setProductDtos(page.getContent().stream().map(converter::entityToDto).toList());
//        List<Product> products = page.getContent();
//        List<ProductDto> productDtos = new ArrayList<>();
//        for (Product p:products) {
//            productDtos.add(converter.entityToDto(p));
//        }
//        pageDto.setProductDtos(productDtos);
        return pageDto;
    }
}
