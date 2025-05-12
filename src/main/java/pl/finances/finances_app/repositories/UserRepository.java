package pl.finances.finances_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.repositories.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity save(UserEntity user);
    boolean existsById(Long id);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByUsername(String username);
    void deleteById(Long id);
    boolean existsByUsername(String username);
}
