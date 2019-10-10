package com.xyzcorp.demos.reactive;


import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@SuppressWarnings("Duplicates")
public class FluxTest {

    @Test
    public void testFluxWithSubscriber() throws InterruptedException {
        Flux<Integer> integerFlux = Flux.create(integerFluxSink -> {
            integerFluxSink.next(10);
            integerFluxSink.next(50);
            integerFluxSink.next(100);
            integerFluxSink.complete();
        });


        integerFlux.repeat(4).map(x -> x * 10).subscribe(new Subscriber<>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(10);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Received Integer:" + integer);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done");
            }
        });

        Thread.sleep(1000);
    }

    @Test
    public void testFluxWithFunctions() {
        Flux<Integer> integerFlux =
                Flux.create(integerFluxSink -> {
                    integerFluxSink.next(10);
                    integerFluxSink.next(50);
                    integerFluxSink.next(100);
                    integerFluxSink.complete();
                });

        integerFlux.map(x -> x + 1)
                   .subscribe(integer ->
                                   System.out.println("Received Integer:" + integer),
                           Throwable::printStackTrace,
                           () -> System.out.println("Done"));
    }


    @Test
    public void testFluxForking() {
        Flux<Integer> fluxSource = Flux.range(1, 10000)
                                       .map(x -> x * 10);
        fluxSource
                .filter(x -> x % 2 == 0)
                .subscribe(integer -> System.out.println("S1:" + integer),
                        Throwable::printStackTrace,
                        () -> System.out.println("S1: Done"));

        fluxSource.map(x -> x - 1).subscribe(integer -> System.out.println(
                "S1:" + integer),
                Throwable::printStackTrace,
                () -> System.out.println("S1: Done"));

    }

    public void  printCurrentThread(String prefix, String value) {
       System.out.println(prefix + ":"  + Thread.currentThread().getName() + " = " + value);
    }

    @Test
    public void testFluxThreading() throws InterruptedException {
        Flux.range(1, 10000)
            .doOnNext(i -> printCurrentThread("P1", String.valueOf(i)))
            .publishOn(Schedulers.newSingle("NewThreadIA"))
            .subscribeOn(Schedulers.elastic())
            .map(x1 -> x1 * 10)
            .doOnNext(i -> printCurrentThread("P2", String.valueOf(i)))
            .filter(x -> x % 2 == 0)
            .doOnNext(i -> printCurrentThread("P3", String.valueOf(i)))
            .subscribe(integer -> printCurrentThread("P4", String.valueOf(integer)),
                    Throwable::printStackTrace,
                    () -> printCurrentThread("P5", "Done"));

        Thread.sleep(10000);
    }
}
