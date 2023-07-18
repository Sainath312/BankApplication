package com.example.Bank.services;

import com.example.Bank.entity.AccountEntity;
import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.execeptions.MoneyException;
import com.example.Bank.execeptions.UserNotFound;
import com.example.Bank.repository.AccountRepo;
import com.example.Bank.repository.TransactionRepo;
import com.example.Bank.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;

import java.util.Optional;


@Service
public class TransferMoney {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransactionRepo transactionRepo;
    public ResponseEntity<String> transferFunds(Transfer transfer) {
        Optional<AccountEntity> s_user = accountRepo.findById(transfer.getSid());
        Optional<AccountEntity> d_user = accountRepo.findById(transfer.getDid());
        if(s_user.isPresent() && d_user.isPresent()){
            AccountEntity s_account = s_user.get();
            AccountEntity d_account = d_user.get();
            if(s_account.getBalance()>=transfer.getAmount() && transfer.getAmount()>0.00){
                double debitMoney = s_account.getBalance()-transfer.getAmount();
                s_account.setBalance(debitMoney);
                accountRepo.save(s_account);
                double creditMoney = d_account.getBalance()+ transfer.getAmount();
                d_account.setBalance(creditMoney);
                accountRepo.save(d_account);
                debit(s_account,transfer.getAmount());
                credit(d_account,transfer.getAmount());
                return ResponseEntity.status(HttpStatus.OK).header("Content-Type","application/json").body("{\"message\": \"Amount "+transfer.getAmount()+" Transfer Successfully Source Account_ID "+transfer.getSid()+" to Destination Account_ID "+transfer.getDid()+"\"}");
            }
            throw new MoneyException("Insufficient User Balance");
        }
        throw new UserNotFound("UserNotFound");

    }
    public void credit(AccountEntity id, double balance) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount_ID(id);
        transaction.setTransactionType("Credited");
        transaction.setAmount(balance);
        transaction.setTransactionDate(LocalDate.now());
        transactionRepo.save(transaction);
    }
    public void debit(AccountEntity id, double balance){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccount_ID(id);
        transaction.setTransactionType("Debit");
        transaction.setAmount(balance);
        transaction.setTransactionDate(LocalDate.now());
        transactionRepo.save(transaction);

    }
}
