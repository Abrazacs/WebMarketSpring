package ru.sergeysemenov.webmarketspring.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.entities.Category;
import ru.sergeysemenov.webmarketspring.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryByCategoryTitle(String category) {
        return categoryRepository.findCategoryByTitle(category);
    }
}
