package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record TransactionResponse(double transactionAmount, String transactionType, LocalDate transactionDate) {
}
