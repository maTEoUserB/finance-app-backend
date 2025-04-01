package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.UserRequest;
import pl.finances.finances_app.dto.UserResponse;
import pl.finances.finances_app.services.UserService;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new-user")
    ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest user) {
        return userService.createNewUser(user);
    }

    @GetMapping("/user/{username}")
    ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return userService.showUserAccount(username);
    }


}
