package com.xyzcorp;

public class MyRunnerWithRunnable {

    public static void main(String[] args) throws InterruptedException {
        System.out.format("Thread starting on %s\n", Thread.currentThread().getName());
        MyRunnable runnable = new MyRunnable(10);
        MyRunnable runnable2 = new MyRunnable(20);

        Thread t = new Thread(runnable);
        t.start();

        Thread t2 = new Thread(runnable2);
        t2.start();

        Thread.sleep(10000);

        System.out.format("Thread ending on %s\n", Thread.currentThread().getName());

        //JVM will end
    }
}
