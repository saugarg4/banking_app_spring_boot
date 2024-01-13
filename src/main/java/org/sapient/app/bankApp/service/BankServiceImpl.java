package org.sapient.app.bankApp.service;

import org.sapient.app.bankApp.dao.BankOperationsDaoImpl;
import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Customer;
import org.sapient.app.bankApp.model.Transaction;
import org.sapient.app.bankApp.repository.BankRepository;
import org.sapient.app.bankApp.repository.CustomerRepository;
import org.sapient.app.bankApp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final BankOperationsDaoImpl bankOperationsDao;
    private final TransactionRepository transactionRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, CustomerRepository customerRepository, BankOperationsDaoImpl bankOperationsDao, TransactionRepository transactionRepository) {
        this.bankOperationsDao = bankOperationsDao;
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction generateTransaction(String operation, Account account) {
        return Transaction.builder().account(account).transactionType(operation).transactionDate(new Date()).build();
    }

    @Override
    public float depositMoney(Long accountNumber, float amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            bankOperationsDao.depositMoney(account, amount);
            bankRepository.save(account);
            transactionRepository.save(generateTransaction("DM", account));
            return account.getBalance();
        }
        return -1;
    }

    @Override
    public float withdrawMoney(Long accountNumber, float amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            if (bankOperationsDao.withdrawMoney(account, amount)) {
                bankRepository.save(account);
                transactionRepository.save(generateTransaction("WM", account));
                return account.getBalance();
            }
            return -2;
        }
        return -1;
    }

    @Override
    public List<Transaction> getTransactionsById(Long accountNumber) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            return transactionRepository.findTransactionsById(accountNumber);
        }
        return null;
    }

    @Override
    public List<Transaction> getTransactionsByIdByType(Long accountNumber, String transactionType) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            return transactionRepository.findTransactionsByIdByType(accountNumber, transactionType);
        }
        return null;
    }

    @Override
    public Account getAccount(Long accountNumber) {
        Optional<Account> account = bankRepository.findById(accountNumber);
        return account.orElse(null);
    }

    @Override
    public float openBankFD(Long accountNumber, float fdAmount, int fdTenure) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            float fdInterest = bankOperationsDao.openBankFD(account, fdAmount, fdTenure);
            if (fdInterest != -1) {
                bankRepository.save(account);
                transactionRepository.save(generateTransaction("FD", account));
                return fdInterest;
            }
            return -2;
        }
        return -1;

    }

    @Override
    public float applyLoan(Long accountNumber, float loanAmount, int loanTenure, int loanType) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            float loanInterest = bankOperationsDao.applyLoan(account, loanAmount, loanTenure, loanType);
            if (loanInterest != -1) {
                bankRepository.save(account);
                transactionRepository.save(generateTransaction("LN", account));
                return loanInterest;
            }
            return -2;
        }
        return -1;
    }

    @Override
    public float applyCreditCard(Long accountNumber, float ccAmount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            float creditServiceCharge = bankOperationsDao.applyCreditCard(account, ccAmount);
            if (creditServiceCharge != -1) {
                bankRepository.save(account);
                transactionRepository.save(generateTransaction("CC", account));
                return creditServiceCharge;
            }
            return -2;
        }
        return -1;
    }

    @Override
    public Account createBankAccount(Customer customer, float amount) {
        if(bankOperationsDao.canCreateAccount(amount)){
            customer = customerRepository.save(customer);
            Account account = new Account(customer, amount);
            bankRepository.save(account);
            transactionRepository.save(generateTransaction("CA", account));
            return account;

        }
        return null;
    }

}
