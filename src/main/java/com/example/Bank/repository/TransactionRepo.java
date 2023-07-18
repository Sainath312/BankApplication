package com.example.Bank.repository;

import com.example.Bank.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {


}
