package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.requestAndResponse.IndexResponse;
import pl.finances.finances_app.dto.requestAndResponse.UserRequest;
import pl.finances.finances_app.dto.requestAndResponse.UserResponse;
import pl.finances.finances_app.services.AccountService;
import pl.finances.finances_app.services.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;
    private final AccountService accountService;

    @Autowired
    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping("/new_user")
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest user) {
        return userService.createNewUser(user);
    }

    @GetMapping("/user/{username}")
    ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return userService.showUserAccount(username);
    }

    @GetMapping("/index/{id}")
    ResponseEntity<IndexResponse> index(@PathVariable long id) {
        return accountService.getMainAccountInformations(id);
    }
}
