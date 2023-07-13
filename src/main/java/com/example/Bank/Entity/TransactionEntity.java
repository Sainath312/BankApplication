package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.DecimalFormat;

@Data
@Entity
@Table(name = "Transactions")

public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TransactionId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="accountID")
    private AccountEntity Account_ID;

    private String TransactionType;

    private DecimalFormat Amount;

    private DateFormat TransactionDateFormat;

}
