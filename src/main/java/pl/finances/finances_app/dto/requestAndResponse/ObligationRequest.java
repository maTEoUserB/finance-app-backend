package pl.finances.finances_app.dto.requestAndResponse;

import java.time.LocalDate;

public record ObligationRequest(String title, double amount, LocalDate dateToPay, long categoryId) {
}
