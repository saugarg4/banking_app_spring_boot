package org.sapient.app.bankApp.service;

import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Customer;
import org.sapient.app.bankApp.model.Transaction;
import org.sapient.app.bankApp.repository.TransactionRepository;

import java.io.BufferedReader;
import java.util.List;

public interface BankService {
    public Transaction generateTransaction(String operation, Account account);
    public float depositMoney(Long accountNumber, float amount);
    public float withdrawMoney(Long accountNumber, float amount);
    public Account getAccount(Long accountNumber);
    public float openBankFD(Long accountNumber, float fdAmount, int fdTenure);
    public float applyLoan(Long accountNumber, float loanAmount, int loanTenure, int loanType);
    public float applyCreditCard(Long accountNumber, float ccAmount);
    public Account createBankAccount(Customer customer, float amount);
    public List<Transaction> getTransactionsById(Long accountNumber);
    public List<Transaction> getTransactionsByIdByType(Long accountNumber, String Operation);
}
