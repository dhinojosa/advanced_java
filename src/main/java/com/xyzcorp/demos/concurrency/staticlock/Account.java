package com.xyzcorp.demos.concurrency.staticlock;

class Account {
    private int amount;

    public Account() {
    }

    //The static makes the class become the lock
    public void deposit(int amount) {
        synchronized (this) {
            System.out.format("Thread %s, Amount: %d%n", Thread.currentThread(), amount);
            this.amount = amount;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getAmount() {
        synchronized (this) {
            return amount;
        }
    }

    public static void main(String[] args) {
        Account a = new Account();
        Account b = new Account();
        Account c = new Account();
        Account d = new Account();
        Account e = new Account();
        Thread t = new Thread(() -> a.deposit(100));
        Thread t1 = new Thread(() -> b.deposit(200));
        Thread t2 = new Thread(() -> c.deposit(300));
        Thread t3 = new Thread(() -> d.deposit(400));
        Thread t4 = new Thread(() -> e.deposit(500));
        t.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
