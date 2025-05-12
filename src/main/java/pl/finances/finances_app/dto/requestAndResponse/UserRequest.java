package pl.finances.finances_app.dto.requestAndResponse;

public record UserRequest(String username, double saldo, String password, String role) {
}
