package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(double transactionAmount, String transactionType, LocalDateTime transactionDate) {
}
