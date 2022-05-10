package ru.sergeysemenov.webmarketspring.core.test;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.sergeysemenov.webmarketspring.api.ProductDto;
import ru.sergeysemenov.webmarketspring.core.converters.ProductConverter;
import ru.sergeysemenov.webmarketspring.core.entities.Category;
import ru.sergeysemenov.webmarketspring.core.repositories.ProductRepository;
import ru.sergeysemenov.webmarketspring.core.services.CategoryService;
import ru.sergeysemenov.webmarketspring.core.services.ProductService;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest(classes = ProductService.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductConverter productConverter;

    @Test
    public void createNewProductTest() {
        Category category = new Category();
        category.setId(1L);
        category.setTitle("Еда");
        category.setProductList(Collections.emptyList());
        Mockito.doReturn(category)
                .when(categoryService)
                .findCategoryByCategoryTitle("Еда");

        ProductDto productDto = new ProductDto(null, "Апельсины", BigDecimal.valueOf(100.0), "Food");
        productService.createNewProduct(productDto);

        Mockito.verify(productRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
