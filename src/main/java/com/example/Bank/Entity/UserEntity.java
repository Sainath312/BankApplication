package com.example.Bank.Entity;

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
    private Long UserID;
    private String UserName;
    private String UserPassword;
    private String EmailID;
    private String PhoneNumber;



    public UserEntity(String userName, String userPassword, String emailID, String phoneNumber) {
        UserName = userName;
        UserPassword = userPassword;
        EmailID = emailID;
        PhoneNumber = phoneNumber;
    }
}
