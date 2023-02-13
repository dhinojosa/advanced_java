package com.xyzcorp.demos.concurrency.reentrantlocks;

/**
 * Our seventh version has an immutable BankAccount. Whenever we need to
 * change the balance, we create a new account. Most Java programmers
 * have a knee-jerk reaction to this:
 * "Ah, but this will create too many objects!"
 * This might be true. However, contended ReentrantLocks also create objects.
 * Thus this argument is not always valid. You might be better off using
 * a non-blocking algorithm that simply creates a new account and
 * writes it into an AtomicReference using a CompareAndSwap (CAS).
 *
 */
public class BankAccountImmutable {
    private final long balance;

    public BankAccountImmutable(long balance) {
        this.balance = balance;
    }

    public BankAccountImmutable deposit(long amount) {
        return new BankAccountImmutable(balance + amount);
    }

    public BankAccountImmutable withdraw(long amount) {
        return new BankAccountImmutable(balance - amount);
    }

    public long getBalance() {
        return balance;
    }
}
