package com.xyzcorp.demos.concurrency.deadlock;

class Friend {
    private final String name;

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public synchronized void bow(Friend bower) {
            System.out.format("%s in %s: %s  has bowed to me!%n",
                this.name, Thread.currentThread(), bower.getName());
            bower.bowBack(this);
    }

    public synchronized void bowBack(Friend bower) {
            System.out.format("%s in %s: %s has bowed back to me!%n",
                this.name, Thread.currentThread(), bower.getName());
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(() -> alphonse.bow(gaston), "Alphonse initiates").start(); //alphonse, gaston
        new Thread(() -> gaston.bow(alphonse), "Gaston initiates").start();
    }
}
