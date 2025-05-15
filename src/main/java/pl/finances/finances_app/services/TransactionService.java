package pl.finances.finances_app.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.finances.finances_app.dto.*;
import pl.finances.finances_app.dto.requestAndResponse.TransactionRequest;
import pl.finances.finances_app.dto.requestAndResponse.TransactionResponse;
import pl.finances.finances_app.repositories.TransactionRepository;
import pl.finances.finances_app.repositories.entities.CategoryEntity;
import pl.finances.finances_app.repositories.entities.TransactionEntity;
import pl.finances.finances_app.repositories.entities.UserEntity;

import java.net.URI;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, UserService userService, CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Transactional
    public ResponseEntity<TransactionResponse> createNewTransaction(TransactionRequest trn) {
        UserEntity user = userService.findUserByUsername("user1").orElseThrow(() -> new EntityNotFoundException("User not found."));
        CategoryEntity category = categoryService.findCategoryById(trn.categoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found.")); //kategoria z bazy
        TransactionEntity newTransaction = new TransactionEntity(trn.transactionTitle(), trn.transactionAmount(), trn.transactionDescription(),
                user, category, trn.transactionType(), trn.transactionDate());
        transactionRepository.save(newTransaction);

        TransactionResponse response = new TransactionResponse(newTransaction.getTransactionAmount(),
                newTransaction.getTransactionType(), newTransaction.getTransactionDate());

        return ResponseEntity.created(URI.create("/new/transaction/" + newTransaction.getTransactionType())).body(response);
    }

    public ResponseEntity<List<LastTransactionsDTO>> getAllTransactions() {
        if(!userService.existsByUsername("user1")){
            return ResponseEntity.notFound().build();
        }
        UserEntity user = userService.findUserByUsername("user1").get();
        List<LastTransactionsDTO> transactions = transactionRepository.getAllTransactions(user.getId());

        return ResponseEntity.ok(transactions);
    }

    public double getWeeklyExpenses(long id){
        return transactionRepository.getLastWeekExpenses(id);
    }

    public double getMeanOfWeeklyExpenses(long id){
        return transactionRepository.getLastWeekAverageExpenses(id);
    }

    public List<TopCategoryDTO> findTopExpenseCategories(long id) {
        return transactionRepository.findTop3ExpenseCategories(id);
    }

    public List<LastTransactionsDTO> findLatestTransactions(long id) {
        return transactionRepository.findLast3Transactions(id);
    }

    public double getBeforeWeekExpenses(long id) {
        return transactionRepository.getBeforeLastWeekExpenses(id);
    }
}
