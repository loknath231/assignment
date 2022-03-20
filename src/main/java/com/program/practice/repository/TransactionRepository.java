package com.program.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.program.practice.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
