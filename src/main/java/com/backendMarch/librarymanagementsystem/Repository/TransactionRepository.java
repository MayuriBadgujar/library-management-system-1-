package com.backendMarch.librarymanagementsystem.Repository;

import com.backendMarch.librarymanagementsystem.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
