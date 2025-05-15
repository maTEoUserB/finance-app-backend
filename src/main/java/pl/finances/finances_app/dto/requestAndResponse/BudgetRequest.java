package pl.finances.finances_app.dto.requestAndResponse;


public record BudgetRequest(long categoryId, double amountLimit) {
}
