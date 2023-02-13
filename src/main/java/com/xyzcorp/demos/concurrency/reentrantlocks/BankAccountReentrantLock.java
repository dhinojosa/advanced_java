package com.xyzcorp.demos.concurrency.reentrantlocks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * The fourth version is similar to the BankAccountSynchronized, except that
 * we are using explicit locks.
 *
 * So what is "better" - ReentrantLock or synchronized? As you can see
 * below, it is a lot more effort to code the ReentrantLock.
 * In Java 5, the performance of contended ReentrantLocks was a lot
 * better than synchronized.
 *
 * However, synchronized was greatly improved for Java 6,
 * so that now the difference is minor.
 *
 * In addition, uncontended synchronized locks can sometimes
 * be automatically optimized away at runtime, leading to great
 * performance improvements over ReentrantLock.
 *
 * The only reason to use ReentrantLock nowadays is if you'd
 * like to use the more advanced features such as better timed waits,
 * tryLock, etc. Performance is no longer a good reason.
 */
public class BankAccountReentrantLock {
    private final Lock lock = new ReentrantLock();
    private long balance;

    public BankAccountReentrantLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public void withdraw(long amount) {
        lock.lock();
        try {
            balance -= amount;
        } finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }
}
