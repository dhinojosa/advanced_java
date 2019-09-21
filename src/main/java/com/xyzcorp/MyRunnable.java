package com.xyzcorp;

import java.time.LocalDateTime;

class MyRunnable implements Runnable {

    private int iterations;

    public MyRunnable(int iterations) {
        this.iterations = iterations;
    }

    public void run() {
        while ((iterations--) > 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                //ignore
            }
            System.out.print(String.format("In Run: [%s] %s\r\n",
                    Thread.currentThread().getName(), LocalDateTime.now()));
        }
    }
}