package com.example.Bank.repository;

import com.example.Bank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
}
