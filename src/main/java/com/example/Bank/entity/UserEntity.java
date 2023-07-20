package com.example.Bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//
@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private long userID;
    private String userName;
    private String userPassword;
    private String emailID;
    private String phoneNumber;

    public UserEntity(String userName, String userPassword, String emailID, String phoneNumber) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.emailID = emailID;
        this.phoneNumber = phoneNumber;
    }
}
