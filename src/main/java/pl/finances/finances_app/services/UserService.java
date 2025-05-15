package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.SavingsGoalDTO;
import pl.finances.finances_app.dto.TopCategoryDTO;
import pl.finances.finances_app.dto.requestAndResponse.IndexResponse;
import pl.finances.finances_app.dto.requestAndResponse.UserRequest;
import pl.finances.finances_app.dto.requestAndResponse.UserResponse;
import pl.finances.finances_app.repositories.UserRepository;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<UserResponse> showUserAccount(String username) {
        if (!userRepository.existsByUsername(username)) {
            return ResponseEntity.notFound().build();
        }

        UserEntity user = findUserByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
        UserResponse userResponse = new UserResponse(user.getId(), user);
        return ResponseEntity.ok(userResponse);
    }

    public ResponseEntity<UserResponse> createNewUser(UserRequest user) {
        UserEntity newUser = new UserEntity(user.username(), user.saldo(), user.password(), user.role());
        userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(newUser.getId(), newUser);
        return ResponseEntity.created(URI.create("/user/" + userResponse.id())).body(userResponse);
    }

    public Optional<UserEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<UserEntity> findUserById(long id) {
        return userRepository.findById(id);
    }

    public boolean existsUserById(long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
