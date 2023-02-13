package com.xyzcorp.demos.concurrency.racecondition;

public class LazyInitRace {
    private ExpensiveObject expensiveObject;

    public ExpensiveObject getInstance() {
        if (expensiveObject == null) {
            expensiveObject = new ExpensiveObject();
        }
        return expensiveObject;
    }
}
