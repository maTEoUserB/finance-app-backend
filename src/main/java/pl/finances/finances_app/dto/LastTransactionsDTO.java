package pl.finances.finances_app.dto;

import java.time.LocalDate;

public interface LastTransactionsDTO {
    String getTransactionTitle();
    double getAmount();
    LocalDate getTransactionDate();
}
