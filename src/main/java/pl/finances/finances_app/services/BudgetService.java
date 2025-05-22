package pl.finances.finances_app.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.requestAndResponse.BudgetRequest;
import pl.finances.finances_app.dto.requestAndResponse.BudgetResponse;
import pl.finances.finances_app.repositories.BudgetRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import pl.finances.finances_app.repositories.entities.BudgetEntity;
import pl.finances.finances_app.repositories.entities.CategoryEntity;

import java.net.URI;

@Service
@Transactional
public class BudgetService {
    private final UserService userService;
    private final BudgetRepository budgetRepository;
    private final CategoryService categoryService;

    @Autowired
    public BudgetService(UserService userService, BudgetRepository budgetRepository, CategoryService categoryService) {
        this.userService = userService;
        this.budgetRepository = budgetRepository;
        this.categoryService = categoryService;
    }


    public ResponseEntity<BudgetResponse> addNewBudget(Jwt jwt, @Valid BudgetRequest budget) {
        String username = jwt.getClaimAsString("preferred_username");
        AccountEntity userAccount = userService.getOrCreateUserAccount(username);
        CategoryEntity category = categoryService.findCategoryById(budget.categoryId()).orElseThrow(() -> new RuntimeException("Category not found"));

        BudgetEntity budgetEntity = new BudgetEntity(userAccount, category, budget.amountLimit());
        budgetRepository.save(budgetEntity);

        BudgetResponse response = new BudgetResponse(category.getCategoryName(), budget.amountLimit());
        return ResponseEntity.created(URI.create("/new/budget/" + budgetEntity.getCategory())).body(response);
    }
}
