package ru.sergeysemenov.webmarketspring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sergeysemenov.webmarketspring.entities.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByTitle(String title);
}