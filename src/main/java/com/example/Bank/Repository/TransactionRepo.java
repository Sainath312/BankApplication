package com.example.Bank.Repository;

import com.example.Bank.Entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<TransactionEntity, Long> {


}
