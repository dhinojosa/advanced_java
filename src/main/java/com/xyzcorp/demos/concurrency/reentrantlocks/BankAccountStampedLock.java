package com.xyzcorp.demos.concurrency.reentrantlocks;

import java.util.concurrent.locks.StampedLock;

/**
 * The ReentrantReadWriteLock had a lot of shortcomings: It suffered from
 * starvation. You could not upgrade a read lock into a write lock. There was
 * no support for optimistic reads. Programmers "in the know" mostly avoided
 * using them.
 *
 * Doug Lea's new Java 8 StampedLock addresses all these shortcomings. With
 * some clever code idioms we can also get better performance. Instead of the
 * usual locking, it returns a long number whenever a lock is granted. This
 * stamp number is then used to unlock again. For example, here is how the
 * code above would look
 *
 * Our sixth version uses StampedLock. I have written two getBalance() methods.
 * The first uses pessimistic read locks, the other optimistic.
 *
 * In our case, since there are no invariants on the fields that would
 * somehow restrict the values, we never need to have a pessimistic lock.
 * Thus the optimistic read is only to ensure memory visibility, much
 * like the BankAccountSynchronizedVolatile approach.
 *
 * In our getBalanceOptimisticRead(), we could retry several times.
 * However, as I said before, if memory visibility is all we
 * care about, then StampedLock is overkill.
 *
 */
public class BankAccountStampedLock {
    private final StampedLock sl = new StampedLock();
    private long balance;

    public BankAccountStampedLock(long balance) {
        this.balance = balance;
    }

    public void deposit(long amount) {
        long stamp = sl.writeLock();
        try {
            balance += amount;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public void withdraw(long amount) {
        long stamp = sl.writeLock();
        try {
            balance -= amount;
        } finally {
            sl.unlockWrite(stamp);
        }
    }

    public long getBalance() {
        long stamp = sl.readLock();
        try {
            return balance;
        } finally {
            sl.unlockRead(stamp);
        }
    }

    public long getBalanceOptimisticRead() {
        long stamp = sl.tryOptimisticRead();
        long balance = this.balance;
        if (!sl.validate(stamp)) {
            stamp = sl.readLock();
            try {
                balance = this.balance;
            } finally {
                sl.unlockRead(stamp);
            }
        }
        return balance;
    }
}
