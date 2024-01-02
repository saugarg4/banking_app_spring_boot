package org.sapient.app.bankApp.repository;

import org.sapient.app.bankApp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Account, Long> {

}
