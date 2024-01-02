package org.sapient.app.bankApp.service;

import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Customer;

import java.io.BufferedReader;

public interface BankService {
    public float depositMoney(Long accountNumber, float amount);
    public float withdrawMoney(Long accountNumber, float amount);
    public Account getAccount(Long accountNumber);
    public float openBankFD(Long accountNumber, float fdAmount, int fdTenure);
    public float applyLoan(Long accountNumber, float loanAmount, int loanTenure, int loanType);
    public float applyCreditCard(Long accountNumber, float ccAmount);
    public Account createBankAccount(Customer customer, float amount);
}
