package org.sapient.app.bankApp.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private float balance;
    private float FDAmount;
    private int fdTenure;
    private float loanAmount;
    private int loanTenure;
    private int loanType;
    private float creditCardMaxLimit;

    public Account(){
        customer = new Customer();
        balance = 0;
        FDAmount = 0;
        loanAmount = 0;
        loanType = 0;
        creditCardMaxLimit = 0;
    }

    public Account(Customer customer, float balance){
        this.customer = customer;
        this.balance = balance;

    }
    public Account(Customer customer){
        this.customer = customer;
    }
    public Long getAccountNumber() {
        return accountNumber;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer(){
        return customer;
    }

    public float getBalance() {
        return balance;
    }

    public int getFdTenure() {
        return fdTenure;
    }

    public void setFdTenure(int fdTenure) {
        this.fdTenure = fdTenure;
    }

    public int getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(int loanTenure) {
        this.loanTenure = loanTenure;
    }
    public void setBalance(float balance) {
        this.balance = balance;
    }
    public float getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(float loanAmount) {
        this.loanAmount = loanAmount;
    }
    public float getFDAmount() {
        return FDAmount;
    }

    public void setFDAmount(float FDAmount) {
        this.FDAmount = FDAmount;
    }
    public float getCreditCardMaxLimit() {
        return creditCardMaxLimit;
    }

    public void setCreditCardMaxLimit(float creditCardMaxLimit) {
        this.creditCardMaxLimit = creditCardMaxLimit;
    }

    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }
}
