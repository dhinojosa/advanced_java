package com.xyzcorp.demos.reactive;


import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class MyPublisherTest {
    @Test
    void testUsingMyPublisher() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        MyPublisher myPublisher = new MyPublisher(executorService);
        myPublisher.subscribe(new Flow.Subscriber<Long>() {
            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(120);
            }

            @Override
            public void onNext(Long item) {
                System.out.format("%s OnNext [%s]: %d%n", "S1", Thread.currentThread(), item);
                if (item == 100L) {
                    this.subscription.cancel();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.format("%s OnError [%s]: %s%n", "S1",  Thread.currentThread(), throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.format("%s OnComplete [%s]%n", "S1",  Thread.currentThread());
                countDownLatch.countDown();
            }
        });


        myPublisher.subscribe(new Flow.Subscriber<Long>() {
            private Flow.Subscription subscription;
            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                this.subscription.request(1000);
            }

            @Override
            public void onNext(Long item) {
                System.out.format("%s OnNext [%s]: %d%n", "S2", Thread.currentThread(), item);
                if (item == 999L) {
                    this.subscription.request(1000);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.format("%s OnError [%s]: %s%n", "S2",  Thread.currentThread(), throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.format("%s OnComplete [%s]%n", "S2",  Thread.currentThread());
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        executorService.shutdown();
    }
}
