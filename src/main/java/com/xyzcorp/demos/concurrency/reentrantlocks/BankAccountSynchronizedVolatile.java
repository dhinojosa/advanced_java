package com.xyzcorp.demos.concurrency.reentrantlocks;

/**
 * The third version uses a combination of the volatile and synchronized
 * keywords. Only one thread at a time can update the balance, but
 * another thread could read the balance in the middle of an update.
 *
 * The volatile modifier is absolutely necessary, since the value might
 * get cached by a thread and so they would not necessarily see the
 * latest value. Also, since it is a 64-bit value, it could get set
 * in two operations on a 32-bit JVM, leading to impossible values.
 * However, this is a very good solution, as it gives us fast reads
 * and is easy to write and understand.
 */
public class BankAccountSynchronizedVolatile {
    private volatile long balance;

    public BankAccountSynchronizedVolatile(long balance) {
        this.balance = balance;
    }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized void withdraw(long amount) {
        balance -= amount;
    }

    public long getBalance() {
        return balance;
    }
}
