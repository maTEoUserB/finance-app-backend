package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record TransactionRequest(String transactionTitle, double transactionAmount, String transactionDescription,
                                 long categoryId, String transactionType, LocalDate transactionDate) {
}
