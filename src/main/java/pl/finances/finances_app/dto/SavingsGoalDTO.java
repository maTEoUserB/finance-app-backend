package pl.finances.finances_app.dto;

import java.time.LocalDate;

public interface SavingsGoalDTO {
    String getTitle();
    LocalDate getDeadline();
}
