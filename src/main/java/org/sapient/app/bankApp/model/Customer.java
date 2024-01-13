package org.sapient.app.bankApp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(nullable = false)
    private String customerName;
    @Column(unique=true, nullable=false)
    private String customerEmail;
    private String customerAddress;
    private String customerGender;
    @Column(unique = true, nullable = false)
    private String customerAadhar;
    private String customerPhone;
}
