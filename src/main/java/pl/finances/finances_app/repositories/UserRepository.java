package pl.finances.finances_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.repositories.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity save(UserEntity user);
    boolean existsById(Integer id);
    Optional<UserEntity> findById(Integer id);
    Optional<UserEntity> findByUsername(String username);
    void deleteById(Integer id);
    boolean existsByUsername(String username);
}
