package com.example.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCredential {
    private String UserName;
    private String UserPassword;
    private String EmailID;
    private String PhoneNumber;
}
