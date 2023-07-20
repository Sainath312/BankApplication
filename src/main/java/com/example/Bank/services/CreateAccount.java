package com.example.Bank.services;

import com.example.Bank.entity.AccountEntity;
import com.example.Bank.entity.UserEntity;
import com.example.Bank.execeptions.UserNotFound;
import com.example.Bank.model.UserCredential;
import com.example.Bank.repository.AccountRepo;
import com.example.Bank.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class CreateAccount {
    @Autowired
    UserRepo userRepo;

    @Autowired
    AccountRepo accountRepo;
    public ResponseEntity<String> createAccount(UserCredential user) {
        UserEntity us = new UserEntity(user.getUserName(),user.getUserPassword(),user.getEmailID(),user.getPhoneNumber());
        UserEntity userEntity = userRepo.save(us);
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
            throw new UserNotFound("Invalid Details Of User");
        }
    }
    private String generateAccountNumber() {
        UUID uuid = UUID.randomUUID();
        long numericValue = Math.abs(uuid.getMostSignificantBits()); // Extract the most significant bits
        String accountNumber = Long.toString(numericValue);
        return accountNumber.substring(0, 12);
    }
}
