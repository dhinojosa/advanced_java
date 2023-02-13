package com.xyzcorp.demos.concurrency.reentrantlocks;

/**
 * The second version uses the synchronized keyword to protect the methods
 * from being called simultaneously by multiple threads on the same
 * BankAccount object. We could either synchronize on "this" or
 * on a private field. For our example, this would not make a difference.
 *
 * In this approach a thread would not be able to read the balance
 * whilst another thread is depositing money.
 */
public class BankAccountSynchronized {
    private long balance;

    public BankAccountSynchronized(long balance) {
        this.balance = balance;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized void withdraw(long amount) {
        balance -= amount;
    }

    public synchronized long getBalance() {
        return balance;
    }
}
