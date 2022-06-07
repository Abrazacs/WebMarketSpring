package ru.sergeysemenov.webmarketspring.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.api.*;
import ru.sergeysemenov.webmarketspring.core.converters.PageConverter;
import ru.sergeysemenov.webmarketspring.core.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.core.entities.Product;
import ru.sergeysemenov.webmarketspring.core.repositories.ProductRepository;
import ru.sergeysemenov.webmarketspring.core.repositories.specifications.ProductSpecification;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final PageConverter pageConverter;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;

    public PageDto findAll(Integer page, Integer pageSize,String titlePart, Integer minPrice, Integer maxPrice){
        if (page < 1) {
            page = 1;
        }
        Specification<Product> specification = createSpecification(titlePart, minPrice, maxPrice);
        Page<Product> originalPage = productRepository.findAll(specification, PageRequest.of(page-1, pageSize));
        return pageConverter.entityToDto(originalPage);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryService.findCategoryByCategoryTitle(productDto.getCategoryTitle()));
        productRepository.save(product);
    }

    public ProductDto findProductById(Long id){
        return productConverter.entityToDto(productRepository.findProductById(id));
    }

    public Optional<Product> findById(Long productId) { return productRepository.findById(productId); }

    private Specification<Product> createSpecification(String titlePart, Integer minPrice, Integer maxPrice) {
        Specification<Product> spec = Specification.where(null);
        if (titlePart != null) {
            spec = spec.and(ProductSpecification.titleLike(titlePart));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpecification.priceGreaterOrEqualsThan(BigDecimal.valueOf(minPrice)));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecification.priceLessThanOrEqualsThan(BigDecimal.valueOf(maxPrice)));
        }
        return spec;
    }
}
