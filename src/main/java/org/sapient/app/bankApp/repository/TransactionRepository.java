package org.sapient.app.bankApp.repository;

import org.sapient.app.bankApp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsById(@Param("accountNumber") Long accountNumber);

    @Query("SELECT t FROM Transaction t WHERE t.account.accountNumber = :accountNumber AND t.transactionType = :transactionType ORDER BY t.transactionDate DESC")
    List<Transaction> findTransactionsByIdByType(@Param("accountNumber") Long accountNumber, @Param("transactionType") String transactionType);


}
