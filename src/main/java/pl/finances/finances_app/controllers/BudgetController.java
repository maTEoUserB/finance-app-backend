package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.finances.finances_app.dto.requestAndResponse.BudgetRequest;
import pl.finances.finances_app.dto.requestAndResponse.BudgetResponse;
import pl.finances.finances_app.services.BudgetService;

@Controller
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/new/budget")
    ResponseEntity<BudgetResponse> addBudget(@Valid @RequestBody BudgetRequest budget) {
        return budgetService.addNewBudget(budget);
    }
}
