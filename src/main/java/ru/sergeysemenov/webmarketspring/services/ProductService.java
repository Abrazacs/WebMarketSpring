package ru.sergeysemenov.webmarketspring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.dtos.CreateNewProductDto;
import ru.sergeysemenov.webmarketspring.dtos.ProductDto;
import ru.sergeysemenov.webmarketspring.entities.Product;
import ru.sergeysemenov.webmarketspring.repositories.ProductRepository;

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

    public void createNewProduct(CreateNewProductDto createNewProductDto) {
        Product product = new Product();
        product.setTitle(createNewProductDto.getTitle());
        product.setPrice(createNewProductDto.getPrice());
        product.setCategory(categoryService.findCategoryByCategoryTitle(createNewProductDto.getCategory()));
        productRepository.save(product);
    }

    public Product findProductById(Long id){
        return productRepository.findProductById(id);
    }

    public Optional<Product> findById(Long productId) { return productRepository.findById(productId); }
}
