package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.SavingsGoalDTO;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalRequest;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalResponse;
import pl.finances.finances_app.repositories.SavingsGoalRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import pl.finances.finances_app.repositories.entities.SavingsGoalEntity;

import java.net.URI;

@Service
@Transactional
public class SavingsGoalService {
    private final UserService userService;
    private final SavingsGoalRepository savingsGoalRepository;

    @Autowired
    public SavingsGoalService(UserService userService, SavingsGoalRepository savingsGoalRepository) {
        this.userService = userService;
        this.savingsGoalRepository = savingsGoalRepository;
    }


    public ResponseEntity<SavingsGoalResponse> createNewSavingsGoal(Jwt jwt, SavingsGoalRequest savingsGoal){
        String username = jwt.getClaimAsString("preferred_username");
        AccountEntity userAccount = userService.getOrCreateUserAccount(username);
        SavingsGoalEntity newSavingsGoal = new SavingsGoalEntity(savingsGoal.title(), userAccount,
                savingsGoal.description(), savingsGoal.currentAmount(), savingsGoal.finalAmount(), savingsGoal.deadline());
        savingsGoalRepository.save(newSavingsGoal);

        SavingsGoalResponse response = new SavingsGoalResponse(newSavingsGoal.getGoalTitle(), newSavingsGoal.getCurrentAmount(),
                newSavingsGoal.getFinalAmmount(), newSavingsGoal.getGoalDeadline());

        return ResponseEntity.created(URI.create("/new/savings_goal/" + newSavingsGoal.getGoalTitle())).body(response);
    }

    public SavingsGoalDTO findLastSavingsGoal(long id) {
        return savingsGoalRepository.findFirstByUserAccount_IdOrderByGoalDeadlineAsc(id)
                .orElseThrow(() -> new EntityNotFoundException("Savings goal not found."));
    }


    public double getCurrentSavingsBalance(AccountEntity userAccount) {
        if(userAccount == null || userAccount.getSavingsGoals() == null || userAccount.getSavingsGoals().isEmpty()) {
            return 0.0;
        }

        return userAccount.getSavingsGoals()
                .stream()
                .mapToDouble(SavingsGoalEntity::getCurrentAmount)
                .sum();
    }
}
