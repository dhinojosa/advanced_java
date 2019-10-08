package com.xyzcorp.demos.reactive;

import com.evolutionnext.other.TickerPriceFinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObservableBasicCreationTest {


    public void printCurrentThread(String marker) {
        String name = Thread.currentThread().getName();
        System.out.println(marker + ":" + name);
    }

    @Test
    public void testManualObservable() throws InterruptedException {
        Observable<Long> longObservable = Observable.just(10L, 20L, 30L, 40L);

        Observable<String> stringObservable =
                longObservable.map(aLong -> "Number: " + aLong);

        stringObservable
                .doOnNext(x -> printCurrentThread("S0"))
                .subscribe(new Observer<String>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        this.disposable = d;
                    }

                    @Override
                    public void onNext(String string) {
                        System.out.println(string);

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Done");
                    }
                });

        stringObservable.doOnNext(x -> printCurrentThread("S1"))
                        .subscribe(System.out::println,
                                Throwable::printStackTrace,
                                () -> System.out.println("Done"));


    }

    @Test
    public void testIntervalWithOneSecond() throws InterruptedException {
        Observable<Long> interval = Observable.interval(1, TimeUnit.SECONDS);

        interval
                .doOnNext(x -> printCurrentThread("S0"))
                .map(x -> "S0:" + x)
                .subscribe(System.out::println);


        interval.doOnNext(x -> printCurrentThread("S1"))
                .filter(x -> x % 2 == 0)
                .map(x -> "S1:" + x)
                .subscribe(System.out::println);

        Thread.sleep(10_000);
    }


    @Test
    public void usingPeek() throws Exception {
        //Java 8 Stream
        List<Integer> integerList = Stream.of(1, 2, 3, 4, 5, 6)
                                          .peek(System.out::println)
                                          .map(x -> x + 1)
                                          .collect(Collectors.toList());
        System.out.println(integerList);
    }

    @Test
    public void testWithObserveOnAndSubscribeOn() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Observable.range(1, 1000)
                  .doOnNext(x -> printCurrentThread("P0:" + x))
                  .subscribeOn(Schedulers.io())
                  .observeOn(Schedulers.computation())
                  .doOnNext(x -> printCurrentThread("P1:" + x))
                  .map(x -> x * 3)
                  .observeOn(Schedulers.newThread())
                  .doOnNext(x -> printCurrentThread("P2:" + x))
                  .filter(x -> x % 2 == 0)
                  .observeOn(Schedulers.io())
                  .doOnNext(x -> printCurrentThread("P3:" + x))
                  .subscribe(System.out::println);

        Thread.sleep(10_000);
    }

    @Test
    public void testMap() {
        Observable<Integer> integerObservable =
                Observable.just(1, 2, 3);

        Observable<String> stringObservable =
                integerObservable
                        .map(integer -> "Hello: " + integer);

        stringObservable
                .subscribe(System.out::println);
    }

    @Test
    public void testBasicFlatMap() throws InterruptedException {
        Observable<Integer> a =
                Observable.just(1, 2, 3);

        Observable<Integer> b = a.flatMap(x ->
                Observable.just(x - 1, x, x + 1));
        b.subscribe(System.out::println);
        System.out.println("-----------");
        Thread.sleep(2000);
        b.map(x -> "Hello:" + x).repeat(4).subscribe(System.out::println);
        Thread.sleep(2000);
    }

    @Test
    public void testFromWithFuture() throws InterruptedException {
        ExecutorService executorService =
                Executors.newCachedThreadPool();

        Future<Integer> future = executorService.submit(
                () -> {
                    System.out.println
                            ("Thread name in future" +
                                    Thread.currentThread().getName());
                    Thread.sleep(1000);
                    return 19;
                });

        Observable<Integer> observable = Observable.fromFuture(future);

        observable.map(x -> x + 30)
                  .doOnNext(x ->
                          System.out.println(Thread.currentThread().getName()))
                  .repeat(5)
                  .subscribe(System.out::println);

        System.out.println(Thread.currentThread().getName());

        observable.flatMap(x -> Observable.just(x + 40, x + 50))
                  .subscribe(System.out::println);

        Thread.sleep(15000);
    }

    @Test
    public void testInterval() throws InterruptedException {
        Observable<String> interval =
                Observable.interval
                        (1, TimeUnit.SECONDS)
                          .map(Long::toHexString);

        interval.doOnNext(x -> System.out.println(Thread.currentThread()
                                                        .getName()))
                .subscribe(lng ->
                        System.out.println("1: lng = " + lng));

        Thread.sleep(5000);
        interval.doOnNext(x -> System.out.println(Thread.currentThread()
                                                        .getName()))
                .subscribe(lng ->
                        System.out.println("2: lng = " + lng));

        Thread.sleep(10000);
    }

    /**
     * Defer will delay any emission of items until an Observer subscribes
     *
     * @throws InterruptedException
     */
    @Test
    public void testDefer() throws InterruptedException {
        Observable<LocalTime> localTimeObservable =
                Observable.defer(
                        () -> Observable
                                .just(LocalTime.now()))
                          .repeat(3);
        localTimeObservable.subscribe(System.out::println);
        Thread.sleep(3000);
        System.out.println("Next Subscriber");
        localTimeObservable.subscribe(System.out::println);
    }


    @Test
    public void testRange() throws InterruptedException {
        Observable<Integer> rangeObservable =
                Observable.range(10, 20);

        rangeObservable.subscribe(System.out::println);

        Thread.sleep(3000);

        System.out.println("-------------");
        System.out.println("Next Subscriber");
        System.out.println("-------------");

        rangeObservable
                .subscribe(System.out::println);
    }


    @Test
    public void testTicker() throws InterruptedException {
        String[] ticker = {"MSFT", "GOOG", "YHOO", "APPL"};
        Observable<String> stockObservable =
                Observable.fromArray(ticker);
        TickerPriceFinder tickerPriceFinder =
                TickerPriceFinder.create();
        stockObservable
                .flatMap(s ->
                        Observable.fromFuture
                                (tickerPriceFinder.getPrice(s)))
                .subscribe(System.out::println);
    }
}
