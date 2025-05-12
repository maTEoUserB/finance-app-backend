package pl.finances.finances_app.dto.requestAndResponse;

import pl.finances.finances_app.repositories.entities.CategoryEntity;

import java.time.LocalDate;

public record ObligationResponse(String obligationTitle, double obligationAmount, LocalDate dateToPay, long categoryId) {
}
