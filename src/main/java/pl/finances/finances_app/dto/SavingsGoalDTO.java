package pl.finances.finances_app.dto;

import java.sql.Date;

public record SavingsGoalDTO(String goalTitle, Date goalDeadline) {
}
