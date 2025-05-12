package pl.finances.finances_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.finances.finances_app.repositories.CategoryRepository;
import pl.finances.finances_app.repositories.entities.CategoryEntity;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Optional<CategoryEntity> findCategoryById(long id) {
        return categoryRepository.findById(id);
    }
}
