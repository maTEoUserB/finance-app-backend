package pl.finances.finances_app.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.requestAndResponse.BudgetRequest;
import pl.finances.finances_app.dto.requestAndResponse.BudgetResponse;
import pl.finances.finances_app.repositories.BudgetRepository;
import pl.finances.finances_app.repositories.entities.BudgetEntity;
import pl.finances.finances_app.repositories.entities.CategoryEntity;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository, UserService userService, CategoryService categoryService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Transactional
    public ResponseEntity<BudgetResponse> addNewBudget(@Valid BudgetRequest budget) {
        UserEntity user = userService.findUserByUsername("user1").orElseThrow(() -> new RuntimeException("User not found"));
        CategoryEntity category = categoryService.findCategoryById(budget.categoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        BudgetEntity budgetEntity = new BudgetEntity(user, category, budget.amountLimit());
        budgetRepository.save(budgetEntity);

        BudgetResponse response = new BudgetResponse(category.getCategoryName(), budget.amountLimit());
        return ResponseEntity.created(URI.create("/new/budget/" + budgetEntity.getCategory())).body(response);
    }
}
