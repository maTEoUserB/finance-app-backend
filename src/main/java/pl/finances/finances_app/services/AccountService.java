package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.NearestObligationsDTO;
import pl.finances.finances_app.dto.TopCategoryDTO;
import pl.finances.finances_app.dto.requestAndResponse.IndexResponse;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AccountService {
    private final UserService userService;
    private final TransactionService transactionService;
    private final SavingsGoalService savingsGoalService;
    private final ExchangeRateService exchangeRateService;
    private final ObligationService obligationService;

    @Autowired
    public AccountService(UserService userService, TransactionService transactionService, SavingsGoalService savingsGoalService, ExchangeRateService exchangeRateService, ObligationService obligationService) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.savingsGoalService = savingsGoalService;
        this.exchangeRateService = exchangeRateService;
        this.obligationService = obligationService;
    }

    @Transactional
    public ResponseEntity<IndexResponse> getMainAccountInformations(long id) {
        if(userService.findUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = userService.findUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        double saldo = user.getSaldo();
        double euroRate = exchangeRateService.getEuroExchangeRate();
        double euroSaldo = new BigDecimal(saldo / euroRate)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        double weeklyExpenses = transactionService.getWeeklyExpenses(id);
        double meanOfWeeklyExpenses = transactionService.getMeanOfWeeklyExpenses(id);
        List<TopCategoryDTO> topCategories = transactionService.findTopExpenseCategories(id);
        double savingsBalance = savingsGoalService.getCurrentSavingsBalance(user);
        double savingsBalanceEuro = new BigDecimal(savingsBalance / euroRate)
                .setScale(2, RoundingMode.HALF_UP).doubleValue();
        List<NearestObligationsDTO> nearestObligations = obligationService.getNearestObligations(id);
        List<LastTransactionsDTO> lastTransactions = transactionService.findLatestTransactions(id);

        IndexResponse response = new IndexResponse(saldo, euroSaldo, weeklyExpenses, meanOfWeeklyExpenses,
                topCategories, savingsBalance, savingsBalanceEuro, nearestObligations, lastTransactions);

        return ResponseEntity.ok(response);
    }
}
