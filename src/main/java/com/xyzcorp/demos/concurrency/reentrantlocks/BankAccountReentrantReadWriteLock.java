package com.xyzcorp.demos.concurrency.reentrantlocks;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * The fifth version uses the ReentrantReadWriteLock, which differentiates
 * between exclusive and non-exclusive locks. In both cases,
 * the locks are pessimistic. This means that if a thread is currently
 * holding the write lock, then any reader thread will get suspended
 * until the write lock is released again.
 *
 * It is thus different to the BankAccountSynchronizedVolatile version,
 * which would allow us to read the balance whilst we were busy updating it.
 *
 * However, the overhead of using a ReentrantReadWriteLock is substantial.
 * As a ballpark figure, we need the read lock to execute for about 2000
 * clock cycles in order to win back the cost of using it.
 *
 * In our case the getBalance() method does substantially less,
 * so we would probably be better off just using a normal ReentrantLock.
 */
public class BankAccountReentrantReadWriteLock {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private long balance;

    public BankAccountReentrantReadWriteLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.writeLock().lock();
        try {
            balance += amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void withdraw(long amount) {
        lock.writeLock().lock();
        try {
            balance -= amount;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public long getBalance() {
        lock.readLock().lock();
        try {
            return balance;
        } finally {
            lock.readLock().unlock();
        }
    }
}
