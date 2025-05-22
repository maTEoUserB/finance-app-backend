package pl.finances.finances_app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.requestAndResponse.IndexResponse;
import pl.finances.finances_app.services.AccountService;

@RestController
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/index")
    ResponseEntity<IndexResponse> index(@AuthenticationPrincipal Jwt jwt) {
        return accountService.getMainAccountInformations(jwt);
    }
}
