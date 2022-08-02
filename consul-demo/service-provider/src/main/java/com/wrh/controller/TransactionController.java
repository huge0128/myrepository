package com.wrh.controller;

import com.wrh.pojo.Transaction;
import com.wrh.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tx")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/tx/{id}")
    @Cacheable(cacheNames = "transaction", key = "#id")
    public Transaction getTransactionById(@PathVariable("id") String id) {
        Transaction transaction = transactionService.findById(id);
        return transaction;
    }

    @CachePut(cacheNames = "transaction", key = "#id")
    @PostMapping("/addTx")
    public Transaction addTx(Transaction transaction) {
        transactionService.
    }
}
