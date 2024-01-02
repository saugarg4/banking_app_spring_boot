package org.sapient.app.bankApp.dao;

import org.sapient.app.bankApp.model.Account;


public interface BankOperationsDao {
    final float minBalance = 1000;
    final float loanPercent = 5;
    final float creditCardPercent = 2;
    final float ROI = 3;
    public void depositMoney(Account account, float amount);
    public boolean withdrawMoney(Account account, float amount);
    public float openBankFD(Account account, float fdAmount, int fdTenure);
    public float applyLoan(Account account, float loanAmount, int loanTenure, int loanType);
    public float applyCreditCard(Account account, float ccAmount);
    public boolean canCreateAccount(float amount);

}

