package org.sapient.app.bankApp.repository;

import org.sapient.app.bankApp.model.CustomerAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerAuthRepository extends JpaRepository<CustomerAuth, Long> {

    Optional<CustomerAuth> findByEmail(String email);

}
