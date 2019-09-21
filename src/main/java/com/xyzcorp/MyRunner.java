package com.xyzcorp;

public class MyRunner {

    public static void main(String[] args) throws InterruptedException {
        System.out.format("Thread starting on %s", Thread.currentThread().getName());
        MyThread myThread = new MyThread();
        myThread.start();

        Thread.sleep(10000);

        myThread.finish();
        System.out.format("Thread ending on %s", Thread.currentThread().getName());

        //JVM will end
    }
}
