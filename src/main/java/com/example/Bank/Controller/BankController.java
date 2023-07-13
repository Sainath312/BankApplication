package com.example.Bank.Controller;

import com.example.Bank.Methods.BankMethods;
import com.example.Bank.model.DepositOrWithdrawMoney;
import com.example.Bank.model.Transfer;
import com.example.Bank.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bank")
public class BankController {
    @Autowired
    BankMethods methods;
    @PostMapping("/createAccount")
    public ResponseEntity<String> createBankAccount(@RequestBody UserCredential user ) throws Exception {
        return methods.createAccount(user);
    }
    @GetMapping("/getBalance/{id}")
    public ResponseEntity<String> getBalanceOfUser(@PathVariable long id){
        return methods.getUserBalance(id);
    }
    @PutMapping("/addBalance")
    public ResponseEntity<String> addMoney(@RequestBody DepositOrWithdrawMoney add){
        return methods.depositMoney(add);
    }
    @PutMapping("/withDrawMoney")
    public ResponseEntity<String> withDraw(@RequestBody DepositOrWithdrawMoney sub){
        return methods.withDrawMoney(sub);
    }
    @PutMapping("/transferFunds/{id}/{id}")
    public ResponseEntity<String> transferFunds(@PathVariable long s_ID, @PathVariable long d_ID, @RequestBody Transfer amount){
        return methods.transferFunds(s_ID,d_ID,amount);
    }
}
