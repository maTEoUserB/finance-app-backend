package pl.finances.finances_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.repositories.entities.BudgetEntity;

@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
    BudgetEntity save(BudgetEntity budget);
}
