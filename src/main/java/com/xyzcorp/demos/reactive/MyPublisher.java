package com.xyzcorp.demos.reactive;

import com.xyzcorp.demos.jol.JOLSample_01_Basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class MyPublisher implements Flow.Publisher<Long> {
    //This is will be available to all subscription
    private final ExecutorService executorService;

    public MyPublisher(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Long> subscriber) {
        //This is local to the subscribe method, which means you can set
        //up some variable that will be closured in the Subscription

        subscriber.onSubscribe(new Flow.Subscription() {
            final AtomicLong requests = new AtomicLong();
            final AtomicLong counter = new AtomicLong();
            final AtomicBoolean done = new AtomicBoolean();
            final AtomicBoolean started = new AtomicBoolean(false);

            //variables here - the scope is the subscription

            @Override
            public void request(long n) {
                requests.addAndGet(n);
                if (!started.get()) {
                    executorService.submit(() -> {
                        started.set(true);
                        while (!done.get() && requests.getAndDecrement() != 0) {
                            subscriber.onNext(counter.incrementAndGet());
                        }
                    });
                }
            }

            @Override
            public void cancel() {
                done.set(true);
            }
        });
    }
}
