package pl.finances.finances_app.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.finances.finances_app.dto.UserRequest;
import pl.finances.finances_app.dto.UserResponse;
import pl.finances.finances_app.repositories.UserRepository;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponse> showUserAccount(String username) {
        if(!userRepository.existsByUsername(username)) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = userRepository.findByUsername(username).get();
        UserResponse userResponse = new UserResponse(user.getId(), user);
        return ResponseEntity.ok(userResponse);
    }

    public ResponseEntity<UserResponse> createNewUser(UserRequest user) {
        UserEntity newUser = new UserEntity(user.username(), user.password());
        newUser.setSaldo(0.0);
        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser.getId(), newUser);
        return ResponseEntity.created(URI.create("/"+userResponse.id())).body(userResponse);
    }
}
