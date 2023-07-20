package com.example.Bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "account_Id")
    private AccountEntity account_ID;

    private String transactionType;
    private double amount;
    private LocalDate transactionDate ;

}
