package pl.finances.finances_app.dto.requestAndResponse;

import pl.finances.finances_app.dto.NearestObligationsDTO;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.TopCategoryDTO;

import java.util.List;

public record IndexResponse(double saldo, double euroSaldo, double weeklyExpenses, double meanOfweeklyExpenses, List<TopCategoryDTO> categories,
                            double savingsBalance, double savingsBalanceEuro, List<NearestObligationsDTO> lastObligations, List<LastTransactionsDTO> lastTransactions) {
}
