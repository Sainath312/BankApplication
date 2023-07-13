package com.example.Bank.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Accounts")
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Account_ID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity UserID;

    private String AccountNumber;
    private double Balance;


    public AccountEntity(long account_ID, double balance) {
        Account_ID = account_ID;
        Balance = balance;
    }

    public AccountEntity(double balance) {
        Balance = balance;
    }
}
