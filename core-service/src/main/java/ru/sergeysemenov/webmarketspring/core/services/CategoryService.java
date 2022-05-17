package ru.sergeysemenov.webmarketspring.core.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sergeysemenov.webmarketspring.core.entities.Category;
import ru.sergeysemenov.webmarketspring.core.repositories.CategoryRepository;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findCategoryByCategoryTitle(String category) {
        return categoryRepository.findCategoryByTitle(category);
    }
}
