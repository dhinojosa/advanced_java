package com.xyzcorp.demos.reactive;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class MyPublisher implements Flow.Publisher<Long> {
    private final ExecutorService executorService;

    public MyPublisher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Long> subscriber) {
        subscriber.onSubscribe(new Flow.Subscription() {
            AtomicLong counter = new AtomicLong(0);
            AtomicBoolean done = new AtomicBoolean(false);

            @Override
            public void request(long n) {
                executorService.submit(() -> {
                    System.out.println("In subscribe:" + Thread.currentThread().getName());
                    if (n < 0) subscriber
                        .onError(
                            new Throwable("count request should be positive"));

                    long goal = counter.get() + n;
                    while (counter.get() < goal && !done.get()) {
                        if (counter.get() == Long.MAX_VALUE) {
                            subscriber.onComplete();
                        }
                        subscriber.onNext(counter.getAndIncrement());
                    }
                });
            }

            @Override
            public void cancel() {
                done.set(true);
            }
        });
    }
}
