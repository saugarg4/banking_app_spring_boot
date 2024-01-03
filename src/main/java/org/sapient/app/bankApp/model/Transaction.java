package org.sapient.app.bankApp.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.TIMESTAMP)
    Date transactionDate;
    @Column(name="type_of_transaction")
    String transactionType;
    @ManyToOne
    @JoinColumn(name = "account_number")
    Account account;
}

