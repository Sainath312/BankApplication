package com.example.Bank.services;

import com.example.Bank.entity.AccountEntity;
import com.example.Bank.execeptions.MoneyException;
import com.example.Bank.repository.AccountRepo;
import com.example.Bank.execeptions.UserNotFound;
import com.example.Bank.model.DepositOrWithdrawMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DepositMoney {
    @Autowired
    AccountRepo accountRepo;
    public ResponseEntity<String> depositMoney(DepositOrWithdrawMoney detail) {
        Optional<AccountEntity> user = accountRepo.findById(detail.getAccount_ID());
        if (user.isPresent()) {
            if (detail.getBalance() >= 1) {
                AccountEntity account = user.get();
                double amount = account.getBalance() + detail.getBalance();
                account.setBalance(amount);
                accountRepo.save(account);
                new TransferMoney().credit(account,detail.getBalance());
                return ResponseEntity.status(HttpStatus.OK).header("Content-Type", "application/json").
                        body("{\"message\": \"Amount " + detail.getBalance() + "/- Is Deposit Successfully \"}");
            }
            throw new MoneyException("Amount MustBe Greater Than 0.00");
        }
        throw new UserNotFound("User Not Present");
    }
}
