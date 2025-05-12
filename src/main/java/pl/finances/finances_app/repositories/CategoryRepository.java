package pl.finances.finances_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.repositories.entities.CategoryEntity;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsById(Long id);
    Optional<CategoryEntity> findById(Long id);
}
