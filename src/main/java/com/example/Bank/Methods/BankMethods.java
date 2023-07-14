package com.example.Bank.Methods;

import com.example.Bank.Entity.AccountEntity;
import com.example.Bank.Entity.TransactionEntity;
import com.example.Bank.Entity.UserEntity;
import com.example.Bank.Repository.AccountRepo;
import com.example.Bank.Repository.TransactionRepo;
import com.example.Bank.Repository.UserRepo;
import com.example.Bank.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BankMethods {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransactionRepo transactionRepo;

    public ResponseEntity<String> getUserBalance(long id)   {
        Optional<AccountEntity> user = accountRepo.findById(id);
        if (user.isPresent()) {
            AccountEntity account = user.get();
            return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").
                    body("{\"Current Balance\": \"" + account.getBalance() + "\"" + "}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").
                body("{\"message\": \"AccountID with " + id + " Is Not Found In The Bank\"}");

    }
    public ResponseEntity<String> createAccount(UserCredential user) {
        UserEntity user2 = new UserEntity(user.getUserName(), user.getUserPassword(), user.getEmailID(), user.getPhoneNumber());
        UserEntity userEntity = userRepo.save(user2);
        Optional<UserEntity> user1 = userRepo.findById(userEntity.getUserID());
        if (user1.isPresent()) {
            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setUserID(user1.get());
            accountEntity.setAccountNumber(generateAccountNumber());
            accountEntity.setBalance(0.00);
            AccountEntity accountEntity1 = accountRepo.save(accountEntity);
            Optional<AccountEntity> account = accountRepo.findById(accountEntity1.getAccount_ID());
            if (account.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).header("Content-Type", "application/json").body("{\"message\": \"Account Created Successfully\"}");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").body("{\"message\": \"Account Creation Failed\"}");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").body("{\"message\": \"Enter Valid User Details\"}");
        }
    }
    private String generateAccountNumber() {
        UUID uuid = UUID.randomUUID();
        long numericValue = Math.abs(uuid.getMostSignificantBits()); // Extract the most significant bits
        String accountNumber = Long.toString(numericValue);
        return accountNumber.substring(0, 12);
    }
    public ResponseEntity<String> depositMoney(DepositOrWithdrawMoney detail) {
        Optional<AccountEntity> user = accountRepo.findById(detail.getAccount_ID());
        if (user.isPresent()) {
            if (detail.getBalance() >= 1) {
                AccountEntity account = user.get();
                double amount = account.getBalance() + detail.getBalance();
                account.setBalance(amount);
                accountRepo.save(account);
//                credit(account,detail.getBalance());
                return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").
                        body("{\"message\": \"Amount " + detail.getBalance() + "/- Is Deposit Successfully \"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body("{\"message\": \"Amount MustBe Greater Than 0.00\"}");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").body("{\"message\": \"AccountID with " + detail.getAccount_ID() + " Is Not Found In The Bank\"}");
    }
    public ResponseEntity<String> withDrawMoney(DepositOrWithdrawMoney details) {
        Optional<AccountEntity> user = accountRepo.findById(details.getAccount_ID());
        if (user.isPresent()) {
            if (details.getBalance() <= 0.00) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body("{\"message\": \"Amount Must Be Greater Than 0.00\"}");
            } else {
                AccountEntity account = user.get();
                if (account.getBalance() < 0.00 && account.getBalance() < details.getBalance()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type", "application/json").body("{\"message\": \" InSufficient Balance\"}");
                } else {
                    double amount = account.getBalance() - details.getBalance();
                    account.setBalance(amount);
                    accountRepo.save(account);
                    debit(account,details.getBalance());
                    return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").body("{\"message\": \"Amount " + details.getBalance() + " WithDraw Successfully From Account ID " + details.getAccount_ID() + "\"}");
                }
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type", "application/json").body("{\"message\": \"Account_ID " + details.getAccount_ID() + " Is Not Found In Bank\"}");
    }
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
//                credit(d_account,transfer.getAmount());
                return ResponseEntity.status(HttpStatus.OK).header("Content-Type","application/json").body("{\"message\": \"Amount "+transfer.getAmount()+" Transfer Successfully Source Account_ID "+transfer.getSid()+" to Destination Account_ID "+transfer.getDid()+"\"}");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("Content-Type","application/json").body("{\"message\": \"InSufficient Balance\"}");
            }
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Content-Type","application/json").body("{\"message\": \"Account Doesn't Found In Bank\"}");
        }

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

//    public List<TransactionEntity> getTransactionsByAccountId(GetTransaction accountId) {
//            return transactionRepo.findByAccount_ID(accountId.getAccountID());
//    }
//    public ResponseEntity<List<TransactionEntity>> getTransactions(GetTransaction accountId) {
//        List<TransactionEntity> transactionList = getTransactionsByAccountId(accountId);
//        if (!transactionList.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.OK).body(transactionList);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
//        }
//    }


}
