package com.banking.banking_system.transaction.entity;

import com.banking.banking_system.bank_account.entity.BankAccount;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.Instant;


    @Getter
    @Setter
    @Entity
    @Table(name = "transactions")
    @Check(constraints = "amount > 0")
    public class Transaction {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "transaction_type", nullable = false, length = 20)
        private String transactionType;

        @Column(name = "amount", nullable = false, precision = 19, scale = 2)
        private BigDecimal amount;

        @Column(name = "description", length = 255)
        private String description;

        @Column(name = "transaction_date", nullable = false)
        private Instant transactionDate;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "bank_account_id", nullable = false)
        private BankAccount bankAccount;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "destination_account_id")
        private BankAccount destinationBankAccount;

        @PrePersist
        protected void onCreate() {
            if (transactionDate == null) {
                transactionDate = Instant.now();
            }
        }
    }
