package com.xyzcorp.demos.reactive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

@SuppressWarnings("Duplicates")
public class FluxBackpressureTest {

    private Flux<Integer> crazedFlux;

    @BeforeEach
    public void setUp() {
        crazedFlux = Flux.create(sink -> {
            int i = 0;
            //noinspection InfiniteLoopStatement
            while (true) {
                sink.next(i);
                i++;
            }
        }, FluxSink.OverflowStrategy.DROP);
    }

    @Test
    public void testFlux() throws InterruptedException {
        crazedFlux.publishOn(Schedulers.newSingle("my-new-thread"))
                .subscribe(n -> {
                    try {
                        Thread.sleep(5); //Wait to fill the buffer some more.
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(n);
                }, Throwable::printStackTrace);
        Thread.sleep(4000);
    }
}
