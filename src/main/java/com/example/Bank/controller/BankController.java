package com.example.Bank.controller;


import com.example.Bank.model.UserCredential;
import com.example.Bank.services.*;
import com.example.Bank.model.DepositOrWithdrawMoney;
import com.example.Bank.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@PropertySource("classpath:custom.properties")
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

    @Value("${mail.host}")
    private String hostName;

    @Value("${mail.password}")
    private String password;

    @Value("${message}")
    private  String custom;
    @Autowired
    WithDraw withDraw;

    public BankController() {
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createBankAccount(@RequestBody UserCredential user ) throws Exception {
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
    @GetMapping("/mySqlHostPassword")
    public ResponseEntity<String> mysqlDetails(){
        return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").body("{\"HostName & Password \": \""+hostName+ " ,"+ password+" \"}");
    }

    @GetMapping("/customPropertiesMessage")
    public ResponseEntity<String> custom(){
     return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").body("{\"Message\":\""+custom+"\"}");
    }

}
