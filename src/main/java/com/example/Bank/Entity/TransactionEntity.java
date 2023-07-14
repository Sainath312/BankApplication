package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "Transactions")
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TransactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_Id")
    private AccountEntity Account_ID;

    private String TransactionType;

    private double Amount;

    private LocalDate transactionDate ;

    public TransactionEntity(AccountEntity account_ID, String transactionType, double amount ) {
        Account_ID = account_ID;
        TransactionType = transactionType;
        Amount = amount;

    }
}
