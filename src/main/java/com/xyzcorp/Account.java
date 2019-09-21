package com.xyzcorp;

class Account {
    private int balance;

    public synchronized void withdrawal(int amount) throws InterruptedException {
        while (true) {
            if (balance - amount < 0)
                wait();
            else
                break;
        }
        balance = -amount;
    }

    public synchronized void deposit(int amount) throws InterruptedException {
        balance += amount;
        notifyAll();
    }
}