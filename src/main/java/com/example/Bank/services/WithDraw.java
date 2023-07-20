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
public class WithDraw {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransferMoney transferMoney;
    public ResponseEntity<String> withDrawMoney(DepositOrWithdrawMoney details) {
        Optional<AccountEntity> user = accountRepo.findById(details.getAccount_ID());
        if (user.isPresent()) {
            if (details.getBalance() <= 0.00) {
                throw new MoneyException("Amount Must Be Greater Than 0.00");
            } else {
                AccountEntity account = user.get();
                if (account.getBalance() < 0.00 && account.getBalance() < details.getBalance()) {
                    throw new MoneyException("InSufficient User Balance");
                } else {
                    double amount = account.getBalance() - details.getBalance();
                    account.setBalance(amount);
                    accountRepo.save(account);
                    transferMoney.debit(account, details.getBalance());
                    return ResponseEntity.status(HttpStatus.FOUND).header("Content-Type", "application/json").body("{\"message\": \"Amount " + details.getBalance() + " WithDraw Successfully From Account ID " + details.getAccount_ID() + "\"}");
                }
            }
        }throw new UserNotFound("User Not Present");
    }
}
