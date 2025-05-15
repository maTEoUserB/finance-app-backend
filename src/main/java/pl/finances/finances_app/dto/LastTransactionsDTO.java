package pl.finances.finances_app.dto;

import java.sql.Date;

public record LastTransactionsDTO(String transactionTitle, double amount, Date transactionDate) {
}
