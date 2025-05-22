package pl.finances.finances_app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.repositories.AccountRepository;
import pl.finances.finances_app.repositories.entities.AccountEntity;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final AccountRepository userRepository;

    @Autowired
    public UserService(AccountRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AccountEntity getOrCreateUserAccount(String username){
        return userRepository.findByUsername(username).orElseGet(()-> {
            AccountEntity userAccount = new AccountEntity(username, 0.0, "USER");
            AccountEntity newUserAccount = userRepository.save(userAccount);
            userRepository.flush();
            return newUserAccount;
        });
    }

    public Optional<AccountEntity> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<AccountEntity> findUserById(long id) {
        return userRepository.findById(id);
    }

    public boolean existsUserById(long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
