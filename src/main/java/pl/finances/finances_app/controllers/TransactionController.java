package pl.finances.finances_app.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.finances.finances_app.dto.LastTransactionsDTO;
import pl.finances.finances_app.dto.requestAndResponse.TransactionRequest;
import pl.finances.finances_app.dto.requestAndResponse.TransactionResponse;
import pl.finances.finances_app.services.TransactionService;

import java.util.List;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/new/transaction")
    ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transaction) {
        return transactionService.createNewTransaction(transaction);
    }

    @GetMapping("/transactions")
    ResponseEntity<List<LastTransactionsDTO>> getTransactions() {
        return transactionService.getAllTransactions();
    }
}
