package com.xyzcorp.demos.concurrency.reentrantlocks;

import java.util.concurrent.atomic.AtomicLong;

/**
 * We need an eighth version, just to satisfy the naysayers who want to
 * see an atomic solution. Here we could either store the balance
 * inside an AtomicLong or inside a volatile long and then use an
 * AtomicLongFieldUpdater or Unsafe to set the field using a
 * CAS (Compare and Swap)
 */
public class BankAccountAtomic {
    private final AtomicLong balance;

    public BankAccountAtomic(long balance) {
        this.balance = new AtomicLong(balance);
    }

    public void deposit(long amount) {
        balance.addAndGet(amount);
    }

    public void withdraw(long amount) {
        balance.addAndGet(-amount);
    }

    public long getBalance() {
        return balance.get();
    }
}
