package com.example.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositOrWithdrawMoney {
    private long account_ID;
    private double balance;
}
