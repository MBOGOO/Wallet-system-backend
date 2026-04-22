package com.wallet.system.controller;

import com.wallet.system.entity.Transaction;
import com.wallet.system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/{walletId}")
    public List<Transaction> getTransactions(@PathVariable Long walletId) {
        return transactionRepository.findByWalletId(walletId);
    }
}