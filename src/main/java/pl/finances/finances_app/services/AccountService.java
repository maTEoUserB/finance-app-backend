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
        if(!userService.existsUserById(id)) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = userService.findUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        double saldo = user.getSaldo();
        double savingsBalance = savingsGoalService.getCurrentSavingsBalance(user);
        double euroRate, usdRate, euroSaldo, usdSaldo, savingsBalanceEuro;
        try {
            euroRate = exchangeRateService.getEuroExchangeRate();
            usdRate = exchangeRateService.getUSDExchangeRate();
            euroSaldo = new BigDecimal(saldo / euroRate)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            usdSaldo = new BigDecimal(saldo/usdRate)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
            savingsBalanceEuro = new BigDecimal(savingsBalance / euroRate)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue();
        }catch (Exception e) {
            euroSaldo = 0.0;
            usdSaldo = 0.0;
            savingsBalanceEuro = 0.0;
        }

        double weeklyExpenses = transactionService.getWeeklyExpenses(id);
        double beforeWeeklyExpenses = transactionService.getBeforeWeekExpenses(id);
        double denominatorOfWeeklyChange = beforeWeeklyExpenses == 0.0 ? weeklyExpenses : beforeWeeklyExpenses;
        double weeklyChange = (weeklyExpenses/denominatorOfWeeklyChange * 100.0) - 100.0;
        double meanOfWeeklyExpenses = transactionService.getMeanOfWeeklyExpenses(id);
        List<TopCategoryDTO> topCategories = transactionService.findTopExpenseCategories(id);
        List<NearestObligationsDTO> nearestObligations = obligationService.getNearestObligations(id);
        List<LastTransactionsDTO> lastTransactions = transactionService.findLatestTransactions(id);

        IndexResponse response = new IndexResponse(saldo, euroSaldo, usdSaldo, weeklyExpenses, meanOfWeeklyExpenses, weeklyChange,
                topCategories, savingsBalance, savingsBalanceEuro, nearestObligations, lastTransactions);

        return ResponseEntity.ok(response);
    }
}
