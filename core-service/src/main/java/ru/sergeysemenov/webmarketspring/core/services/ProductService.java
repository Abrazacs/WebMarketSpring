package ru.sergeysemenov.webmarketspring.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.api.*;
import ru.sergeysemenov.webmarketspring.core.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.core.entities.Product;
import ru.sergeysemenov.webmarketspring.core.repositories.ProductRepository;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryService categoryService;

    public List<ProductDto> findAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product p:products) {
            productDtoList.add(productConverter.entityToDto(p));
        }
        return productDtoList;
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
}
