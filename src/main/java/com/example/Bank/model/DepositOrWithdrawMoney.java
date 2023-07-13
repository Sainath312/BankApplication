package com.example.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositOrWithdrawMoney {
    private long Account_ID;
    private double Balance;
}
