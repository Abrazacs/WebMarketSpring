package ru.sergeysemenov.webmarketspring.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeysemenov.webmarketspring.core.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findProductById(Long id);

}
