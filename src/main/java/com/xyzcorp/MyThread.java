package com.xyzcorp;

import java.time.LocalDateTime;

class MyThread extends Thread {
    private boolean done = false;

    public void finish() {
        this.done = true;
    }

    public void run() {
        while (!done) {
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
