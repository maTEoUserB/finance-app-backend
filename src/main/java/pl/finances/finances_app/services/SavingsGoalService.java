package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.SavingsGoalDTO;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalRequest;
import pl.finances.finances_app.dto.requestAndResponse.SavingsGoalResponse;
import pl.finances.finances_app.repositories.SavingsGoalRepository;
import pl.finances.finances_app.repositories.entities.SavingsGoalEntity;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;

@Service
public class SavingsGoalService {
    private final SavingsGoalRepository savingsGoalRepository;
    private final UserService userService;

    @Autowired
    public SavingsGoalService(SavingsGoalRepository savingsGoalRepository, UserService userService) {
        this.savingsGoalRepository = savingsGoalRepository;
        this.userService = userService;
    }

    @Transactional
    public ResponseEntity<SavingsGoalResponse> createNewSavingsGoal(SavingsGoalRequest savingsGoal){

        UserEntity user = userService.findUserByUsername("user1")
                .orElseThrow(() -> new EntityNotFoundException("User not found."));
        SavingsGoalEntity newSavingsGoal = new SavingsGoalEntity(savingsGoal.title(), user,
                savingsGoal.description(), savingsGoal.currentAmount(), savingsGoal.finalAmmount(), savingsGoal.deadline());
        savingsGoalRepository.save(newSavingsGoal);

        SavingsGoalResponse response = new SavingsGoalResponse(newSavingsGoal.getTitle(), newSavingsGoal.getCurrentAmount(),
                newSavingsGoal.getFinalAmmount(), newSavingsGoal.getDeadline());

        return ResponseEntity.created(URI.create("/new_savings_goal/" + newSavingsGoal.getId())).body(response);
    }

    public SavingsGoalDTO findLastSavingsGoal(long id) {
        return savingsGoalRepository.findFirstByUserIdOrderByDeadlineAsc(id)
                .orElseThrow(() -> new EntityNotFoundException("Savings goal not found."));
    }

    @Transactional
    public double getCurrentSavingsBalance(UserEntity user) {
        return user.getSavingsGoals()
                .stream()
                .mapToDouble(SavingsGoalEntity::getCurrentAmount)
                .sum();
    }
}
