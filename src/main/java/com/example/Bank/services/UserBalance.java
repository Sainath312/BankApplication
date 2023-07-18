package com.example.Bank.services;

import com.example.Bank.entity.AccountEntity;
import com.example.Bank.repository.AccountRepo;
import com.example.Bank.execeptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserBalance {
    @Autowired
    AccountRepo accountRepo;
    public ResponseEntity<String> getUserBalance(long id)   {
        Optional<AccountEntity> user = accountRepo.findById(id);
        if (user.isPresent()) {
            AccountEntity account = user.get();
            return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").
                    body("{\"Current Balance\": \"" + account.getBalance() + "\"" + "}");
        }
        throw new UserNotFound("User Not Present");

    }
}
