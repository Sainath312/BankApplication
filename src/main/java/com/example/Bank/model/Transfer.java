package com.example.Bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transfer {
    private Long sid;
    private Long did;
    private double amount;
}
