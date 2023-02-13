package com.xyzcorp.demos.concurrency.deadlock;

import java.util.concurrent.locks.ReentrantLock;

class Friend {
    private final String name;
    private final ReentrantLock lock;

    public Friend(String name, ReentrantLock lock) {
        this.name = name;
        this.lock = lock;
    }

    public String getName() {
        return this.name;
    }

    public void bow(Friend bower) {
        lock.lock();
        try {
            System.out.format("%s in %s: %s  has bowed to me!%n",
                this.name, Thread.currentThread(), bower.getName());
            bower.bowBack(this);
        } finally {
            lock.unlock();
        }
    }

    public void bowBack(Friend bower) {
        lock.lock();
        try {
            System.out.format("%s in %s: %s has bowed back to me!%n",
                this.name, Thread.currentThread(), bower.getName());
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        final Friend alphonse = new Friend("Alphonse", lock);
        final Friend gaston = new Friend("Gaston", lock);
        new Thread(() -> alphonse.bow(gaston), "Alphonse initiates").start(); //alphonse, gaston
        new Thread(() -> gaston.bow(alphonse), "Gaston initiates").start();
    }
}
