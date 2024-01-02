package org.sapient.app.bankApp.service;

import org.sapient.app.bankApp.dao.BankOperationsDaoImpl;
import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Customer;
import org.sapient.app.bankApp.repository.BankRepository;
import org.sapient.app.bankApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final CustomerRepository customerRepository;
    private final BankOperationsDaoImpl bankOperationsDao;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, CustomerRepository customerRepository, BankOperationsDaoImpl bankOperationsDao) {
        this.bankOperationsDao = bankOperationsDao;
        this.bankRepository = bankRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public float depositMoney(Long accountNumber, float amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            bankOperationsDao.depositMoney(account, amount);

            bankRepository.save(account);
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
                return account.getBalance();
            }
            return -2;
        }
        return -1;
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
            return bankRepository.save(account);
        }
        return null;
    }
}
