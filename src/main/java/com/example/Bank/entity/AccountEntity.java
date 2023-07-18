package com.example.Bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "account_Id")
    private long account_ID;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity userID;

    private String accountNumber;
    private double balance;
}
