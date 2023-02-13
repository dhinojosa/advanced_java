package com.xyzcorp.demos.concurrency.daemon;

public class DaemonRunner {
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.printf("Thread: %s, Daemon: %b\n",
                            Thread.currentThread(),
                            Thread.currentThread().isDaemon());
                        Thread.sleep(2000);

                        System.out.println("Going...");
                    }
                } catch (InterruptedException e) {
                    System.out.println("But why?");
                    e.printStackTrace();
                }
            }
        };

        Thread t = new Thread(runnable);
        Thread t1 = new Thread(runnable);

        t.setDaemon(true); //Run first then uncomment
        t1.setDaemon(false);
        t.start();
        t1.start();


        Thread.sleep(4000);
        t1.interrupt();
        t1.join();
    }
}
