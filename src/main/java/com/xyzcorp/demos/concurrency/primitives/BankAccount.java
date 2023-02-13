package com.xyzcorp.demos.concurrency.primitives;

public class BankAccount {
    private int balance;

    public BankAccount(int i) {
        this.balance = i;
    }

    public synchronized void deposit(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Bad, don't do that");
        this.balance += amount;
        notify();
    }

    public synchronized int withdrawal(int amount) {
        while ((this.balance - amount) <= 0) {
            try {
                wait();  //thread waits here.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.balance -= amount;
        return amount;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(ProcessHandle.current().pid());
        for (int i = 0; i < 100; i++) {
            BankAccount bankAccount = new BankAccount(0);
            new Thread(() -> {
                bankAccount.deposit(1000);
            }).start();
            Thread.sleep(1000);
            new Thread(() -> {
                bankAccount.withdrawal(3000);
            }).start();
            Thread.sleep(1000);
            new Thread(() -> {
                bankAccount.deposit(200);
            }).start();
            Thread.sleep(1000);
            new Thread(() -> {
                bankAccount.deposit(500);
            }).start();
            Thread.sleep(1000);
            new Thread(() -> {
                bankAccount.deposit(7000);
            }).start();
            Thread.sleep(1000);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(bankAccount.getBalance());
            System.out.println("Balance should be : " + (1000 + 200 + 500 + 7000 - 3000));
        }
    }
}
