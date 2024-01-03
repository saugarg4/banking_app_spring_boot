package org.sapient.app.bankApp.controller;

import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Transaction;
import org.sapient.app.bankApp.service.BankServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bank")
public class TransactionController {

    private final BankServiceImpl bankService;

    public TransactionController(BankServiceImpl bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/getTransactions")
    public ResponseEntity<List<Transaction>> getTransactionsById(@RequestBody Map<String, String> json) {
        try {

            Long accountNumber = Long.valueOf(json.get("accountNumber"));
            List<Transaction> transactions = bankService.getTransactionsById(accountNumber);
            if (transactions == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getTransactions/{transactionType}")
    public ResponseEntity<List<Transaction>> getTransactionsByIdByType(@PathVariable("transactionType") String transactionType, @RequestBody Map<String, String> json) {
        try {

            Long accountNumber = Long.valueOf(json.get("accountNumber"));
            List<Transaction> transactions = bankService.getTransactionsByIdByType(accountNumber, transactionType);
            if (transactions == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
