package pl.finances.finances_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.TopCategoryDTO;
import pl.finances.finances_app.dto.requestAndResponse.TransactionResponse;
import pl.finances.finances_app.repositories.entities.TransactionEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    TransactionEntity save(TransactionEntity transaction);
    boolean existsById(Long id);
    Optional<TransactionEntity> findById(Long id);
    void deleteById(Long id);

    @Query(value = """
    SELECT c.category_name AS categoryName, SUM(t.transaction_amount) AS totalAmount, SUM(t.transaction_amount)/NULLIF(SUM(b.amount_limit), 0) AS budgetProcent
    FROM transaction_entity t
    JOIN category_entity c ON t.category_id = c.id
    LEFT JOIN budget_entity b ON b.category_id = t.category_id AND b.user_id = t.user_id
    WHERE t.user_id = :id AND c.type_for_category = 'expense'
    GROUP BY c.id, c.category_name
    ORDER BY totalAmount DESC
    LIMIT 3
""", nativeQuery = true)
    List<TopCategoryDTO> findTop3ExpenseCategories(@Param("id") long id);

    @Query(value = """
    SELECT t.transaction_title AS transactionTitle, t.transaction_amount AS amount, t.transaction_date AS transactionDate
    FROM transaction_entity t
    WHERE t.user_id = :id
    ORDER BY t.transaction_date DESC
    LIMIT 3
""", nativeQuery = true)
    List<LastTransactionsDTO> findLast3Transactions(@Param("id") long id);


    @Query(value = """
    SELECT COALESCE(SUM(t.transaction_amount), 0) AS totalAmount
    FROM transaction_entity t
    WHERE t.user_id = :id
      AND t.transaction_type = 'expense'
      AND t.transaction_date >= NOW() - INTERVAL '1 week'
      AND t.transaction_date < NOW()
""", nativeQuery = true)
    double getLastWeekExpenses(@Param("id") long id);

    @Query(value = """
    SELECT COALESCE(AVG(t.transaction_amount), 0) AS averageAmount
    FROM transaction_entity t
    WHERE t.user_id = :id
      AND t.transaction_type = 'expense'
      AND t.transaction_date >= NOW() - INTERVAL '1 week'
      AND t.transaction_date < NOW()
""", nativeQuery = true)
    double getLastWeekAverageExpenses(@Param("id") long id);

    @Query(value = """
    SELECT COALESCE(SUM(t.transaction_amount), 0) AS beforeTotalAmount
    FROM transaction_entity t
    WHERE t.user_id = :id
      AND t.transaction_type = 'expense'
      AND t.transaction_date >= NOW() - INTERVAL '2 week'
      AND t.transaction_date < NOW() - INTERVAL '1 week' 
""", nativeQuery = true)
    double getBeforeLastWeekExpenses(@Param("id") long id);

    @Query(value = """
    SELECT t.transaction_title AS transactionTitle, t.transaction_amount AS amount, t.transaction_date AS transactionDate
    FROM transaction_entity t
    WHERE t.user_id = :id
    ORDER BY t.transaction_date DESC
""", nativeQuery = true)
    List<LastTransactionsDTO> getAllTransactions(@Param("id") long id);
}
