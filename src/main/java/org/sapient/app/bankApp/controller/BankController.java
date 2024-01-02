package org.sapient.app.bankApp.controller;

import org.sapient.app.bankApp.model.Account;
import org.sapient.app.bankApp.model.Customer;
import org.sapient.app.bankApp.service.BankServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankServiceImpl bankService;

    @Autowired
    public BankController(BankServiceImpl bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/createAccount/{amount}")
    public ResponseEntity<Account> createAccount(@PathVariable("amount") float amount, @RequestBody Customer customer) {
        try {
            Account account = bankService.createBankAccount(customer, amount);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAccount")
    public ResponseEntity<Account> getAccount(@RequestBody Map<String, String> json) {
        try {

            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            Account account = bankService.getAccount(accountNumber);
            if (account == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/depositMoney/{amount}")
    public ResponseEntity<Float> depositMoney(@PathVariable("amount") float amount, @RequestBody Map<String, String> json) {
        try {
            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            float balance = bankService.depositMoney(accountNumber, amount);
            if (balance == -1) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/withdrawMoney/{amount}")
    public ResponseEntity<Float> withdrawMoney(@PathVariable("amount") float amount, @RequestBody Map<String, String> json) {
        try {
            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            float balance = bankService.withdrawMoney(accountNumber, amount);
            if (balance == -1) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else if (balance == -2) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(balance, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //
    @PutMapping("/applyFD/{amount}/{fdTenure}")
    public ResponseEntity<Float> applyFD(@PathVariable("amount") float amount, @PathVariable("fdTenure") int fdTenure, @RequestBody Map<String, String> json) {
        try {
            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            float fdInterset = bankService.openBankFD(accountNumber, amount, fdTenure);
            if (fdInterset == -1) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            if (fdInterset == -2) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(fdInterset, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/applyLoan/{loanType}/{amount}/{loanTenure}")
    public ResponseEntity<Float> applyLoan(@PathVariable("amount") float amount, @PathVariable("loanTenure") int loanTenure, @PathVariable("loanType") int loanType, @RequestBody Map<String, String> json) {
        try {
            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            float loanInterset = bankService.applyLoan(accountNumber, amount, loanTenure, loanType);
            if (loanInterset == -1) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            if (loanInterset == -2) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(loanInterset, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/applyCreditCard/{amount}")
    public ResponseEntity<Float> applyCreditCard(@PathVariable("amount") float amount, @RequestBody Map<String, String> json) {
        try {
            String val = json.get("accountNumber");
            Long accountNumber = Long.valueOf(val);
            float creditServiceCharge = bankService.applyCreditCard(accountNumber, amount);
            if (creditServiceCharge == -1) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            if (creditServiceCharge == -2) {
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(creditServiceCharge, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
