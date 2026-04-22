package com.wallet.system.service;

import com.wallet.system.entity.User;
import com.wallet.system.entity.Wallet;
import com.wallet.system.entity.Transaction;
import com.wallet.system.repository.WalletRepository;
import com.wallet.system.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wallet.system.repository.UserRepository;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public Wallet getWallet(Long userId) {
        return walletRepository.findByUserId(userId);
    }
    // =========================
    // 1. CREATE WALLET
    // =========================
    public Wallet createWallet(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);
        return walletRepository.save(wallet);
    }

    // =========================
    // 2. DEPOSIT
    // =========================
    public Wallet deposit(Long userId, double amount) {

        Wallet wallet = walletRepository.findByUserId(userId);

        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        Transaction tx = new Transaction("DEPOSIT", amount, wallet);
        transactionRepository.save(tx);

        return wallet;
    }

    // =========================
    // 3. WITHDRAW
    // =========================
    public Wallet withdraw(Long userId, double amount) {

        Wallet wallet = walletRepository.findByUserId(userId);

        if (wallet.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        Transaction tx = new Transaction("WITHDRAW", amount, wallet);
        transactionRepository.save(tx);

        return wallet;
    }

    // =========================
    // 4. TRANSFER MONEY
    // =========================
    public String transfer(Long fromUserId, Long toUserId, double amount) {

        Wallet sender = walletRepository.findByUserId(fromUserId);
        Wallet receiver = walletRepository.findByUserId(toUserId);

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        walletRepository.save(sender);
        walletRepository.save(receiver);

        Transaction tx1 = new Transaction("TRANSFER_OUT", amount, sender);
        Transaction tx2 = new Transaction("TRANSFER_IN", amount, receiver);

        transactionRepository.save(tx1);
        transactionRepository.save(tx2);

        return "Transfer successful";
    }
    public String testWalletService() {
        return "OK";
    }}
