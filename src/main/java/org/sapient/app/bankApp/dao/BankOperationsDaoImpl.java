package org.sapient.app.bankApp.dao;

import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class BankOperationsDaoImpl implements BankOperationsDao {

    @Override
    public void depositMoney(Account account, float depositAmount) {
        account.setBalance(account.getBalance() + depositAmount);
    }

    @Override
    public boolean withdrawMoney(Account account, float withdrawAmount) {
        float currentBalance = account.getBalance() - withdrawAmount;
        if (currentBalance >= minBalance) {
            account.setBalance(currentBalance);
            return true;
        }
        return false;
    }

    public boolean isEligibleForFD(Account account) {
        return account.getFDAmount() == 0;
    }

    @Override
    public float openBankFD(Account account, float fdAmount, int fdTenure) {
        if (isEligibleForFD(account)) {
            float currAmount = fdAmount;
            for (int i = 1; i <= fdTenure; i++) {
                currAmount = currAmount * (1 + ROI / 100);
            }
            account.setFDAmount(fdAmount);
            account.setFdTenure(fdTenure);
            return currAmount - fdAmount;
        }
        return -1;
    }

    public boolean isEligibleForLoan(Account account) {
        return account.getBalance() >= (minBalance*2) && (account.getLoanType() == 0) ;
    }
    public boolean isEligibleForCreditCard(Account account) {
        return account.getBalance() >= (minBalance*2) && (account.getCreditCardMaxLimit() == 0) ;
    }
    @Override
    public float applyLoan(Account account, float loanAmount, int loanTenure, int loanType) {
        if(isEligibleForLoan(account)){

            account.setLoanTenure(loanTenure);
            account.setLoanType(loanType);
            account.setLoanAmount(loanAmount);

            float loanInterest = 0;
            for(int i =1; i<= loanTenure; i++){
                loanAmount += loanInterest;
                loanInterest = loanAmount * loanPercent * i / 100;
            }
            return loanInterest;
        }
        return -1;
    }

    @Override
    public float applyCreditCard(Account account, float ccAmount) {
        if(isEligibleForCreditCard(account)){

            account.setCreditCardMaxLimit(ccAmount);

            float creditServiceCharge = account.getBalance() * creditCardPercent / 100;
            account.setBalance(account.getBalance() - creditServiceCharge);
            return creditServiceCharge;
        }
        return -1;
    }

    @Override
    public boolean canCreateAccount(float amount){
        return amount >= minBalance;
    }
}
