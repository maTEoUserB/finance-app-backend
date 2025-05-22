package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionRequest(String transactionTitle, double transactionAmount, String transactionDescription,
                                 long categoryId, String transactionType, LocalDateTime transactionDate) {
}
