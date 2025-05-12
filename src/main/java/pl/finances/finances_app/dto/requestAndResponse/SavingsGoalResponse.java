package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record SavingsGoalResponse(String title, double currentAmount, double finalAmmount, LocalDate deadline) {
}
