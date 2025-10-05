package com.own.springboot_10best.bank;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService {
    private final AccountRepository repo;
    public BankService(AccountRepository repo) { this.repo = repo; }

    @Transactional
    public void transfer(String fromNumber, String toNumber, double amount) {
        Account from = repo.findByNumber(fromNumber).orElseThrow();
        Account to = repo.findByNumber(toNumber).orElseThrow();
        if (from.getBalance() < amount) throw new IllegalArgumentException("Insufficient funds");
        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);
    }
}
