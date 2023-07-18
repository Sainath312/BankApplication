package com.example.Bank.controller;

import com.example.Bank.entity.UserEntity;
import com.example.Bank.services.*;
import com.example.Bank.model.DepositOrWithdrawMoney;
import com.example.Bank.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bank")
public class BankController {
    @Autowired
    TransferMoney methods;
    @Autowired
    UserBalance userBalance;
    @Autowired
    CreateAccount account;

    @Autowired
    DepositMoney deposit;

    @Autowired
    WithDraw withDraw;
    @PostMapping("/createAccount")
    public ResponseEntity<String> createBankAccount(@RequestBody UserEntity user ) throws Exception {


     return account.createAccount(user);

    }
    @GetMapping("/balance/{id}")
    public ResponseEntity<String> getBalanceOfUser(@PathVariable long id){
        return userBalance.getUserBalance(id);
    }
    @PutMapping("/addBalance")
    public ResponseEntity<String> addMoney(@RequestBody DepositOrWithdrawMoney add){
        return deposit.depositMoney(add);
    }
    @PutMapping("/withDrawMoney")
    public ResponseEntity<String> withDraw(@RequestBody DepositOrWithdrawMoney sub){
        return withDraw.withDrawMoney(sub);
    }
    @PutMapping("/transferFunds")
    public ResponseEntity<String> transferFunds(@RequestBody Transfer transfer){
        return methods.transferFunds(transfer);
    }


}
