package pl.finances.finances_app.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.requestAndResponse.CategoryResponse;
import pl.finances.finances_app.services.CategoryService;

@Controller
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/incomes/categories")
    ResponseEntity<CategoryResponse> getIncomeCategories(){
        return categoryService.findAllCategories("income");
    }

    @GetMapping("/expenses/categories")
    ResponseEntity<CategoryResponse> getExpenseCategories(){
        return categoryService.findAllCategories("expense");
    }
}
