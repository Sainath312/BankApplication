package com.example.Bank.Repository;

import com.example.Bank.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<AccountEntity, Long> {
}
