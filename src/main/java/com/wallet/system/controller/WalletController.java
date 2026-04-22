package com.wallet.system.controller;

import com.wallet.system.entity.Wallet;
import com.wallet.system.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/test")
    public String test() {
        return walletService.testWalletService();
    }
    @GetMapping("/{userId}")
    public Wallet getWallet(@PathVariable Long userId) {
        return walletService.getWallet(userId);
    }

    @PostMapping("/deposit")
    public Wallet deposit(@RequestParam Long userId,
                          @RequestParam double amount) {
        return walletService.deposit(userId, amount);
    }

    @PostMapping("/withdraw")
    public Wallet withdraw(@RequestParam Long userId,
                           @RequestParam double amount) {
        return walletService.withdraw(userId, amount);
    }
}