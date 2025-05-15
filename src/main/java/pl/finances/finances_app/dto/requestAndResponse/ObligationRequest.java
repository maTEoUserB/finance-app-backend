package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record ObligationRequest(String title, double amount, boolean isDone, LocalDate dateToPay, long categoryId) {
}
