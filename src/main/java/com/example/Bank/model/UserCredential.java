package com.example.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredential {
    private long userID;
    private String userName;
    private String userPassword;
    private String emailID;
    private String phoneNumber;
}
