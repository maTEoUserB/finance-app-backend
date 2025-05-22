package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalRequest;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalResponse;
import pl.finances.finances_app.services.SavingsGoalService;

@RestController
public class SavingsGoalController {
    private final SavingsGoalService savingsGoalService;

    @Autowired
    public SavingsGoalController(SavingsGoalService savingsGoalService) {
        this.savingsGoalService = savingsGoalService;
    }

    @PostMapping("/new/savings_goal")
    ResponseEntity<SavingsGoalResponse> createSavingsGoal(@AuthenticationPrincipal Jwt jwt, @RequestBody @Valid SavingsGoalRequest savingsGoal) {
        return savingsGoalService.createNewSavingsGoal(jwt, savingsGoal);
    }
}
