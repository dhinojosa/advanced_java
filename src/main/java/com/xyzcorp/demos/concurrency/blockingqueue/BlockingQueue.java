package com.xyzcorp.demos.concurrency.blockingqueue;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BlockingQueue {
    private List<Object> queue = new LinkedList<>();
    private int  limit = 10;

    public BlockingQueue(int limit){
        this.limit = limit;
    }

    public synchronized void enqueue(Object item)
        throws InterruptedException  {
        System.out.println("Enqueuing item: " + item);
        while(this.queue.size() == this.limit) {
            wait();
        }
        this.queue.add(item);
        System.out.println("Item Added" + item);
        if(this.queue.size() == 1) {
            notifyAll();
        }
    }


    public synchronized Object dequeue()
        throws InterruptedException{
        while(this.queue.size() == 0){
            wait();
        }

        boolean previouslyFull =
            this.queue.size() == this.limit;

        Object o = this.queue.remove(0);
        System.out.println("Removed" + o);

        if(previouslyFull){
            notifyAll();
        }

        return o;
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //put stuff in the queue
        for (int i = 0; i < 10; i ++) {
            System.out.println("Mark1");
            executorService.submit(() -> {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    System.out.println("Putting into queue: " + now);
                    Thread.sleep(10000);
                    blockingQueue.enqueue(now);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Mark2");
        }

        for (int i = 0; i < 5; i ++) {
            executorService.submit(() -> {
                try {
                    Object item = blockingQueue.dequeue();
                    System.out.println("Got something out: " + item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
