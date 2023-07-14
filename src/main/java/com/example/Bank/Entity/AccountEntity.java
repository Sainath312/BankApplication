package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_Id")
    private long Account_ID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity UserID;

    private String AccountNumber;
    private double Balance;

    @OneToMany(fetch = FetchType.LAZY)
    private List<TransactionEntity> transactionEntities;


    public AccountEntity(long account_ID, double balance) {
        Account_ID = account_ID;
        Balance = balance;
    }

    public AccountEntity(long sid,long did,double balance) {
        Balance = balance;
    }
}
