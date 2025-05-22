package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record SavingsGoalRequest(String title, String description, double currentAmount,
                                 double finalAmount, LocalDate deadline) {
}
