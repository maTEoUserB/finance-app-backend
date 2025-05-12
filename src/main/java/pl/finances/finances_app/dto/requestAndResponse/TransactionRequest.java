package pl.finances.finances_app.dto.requestAndResponse;

public record TransactionRequest(String transactionTitle, double transactionAmount, String transactionDescription,
                                 long categoryId, String transactionType) {
}
