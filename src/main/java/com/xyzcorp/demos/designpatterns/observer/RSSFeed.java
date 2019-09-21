package com.xyzcorp.demos.designpatterns.observer;

public interface RSSFeed {
    void broadcast(RSSEntry entry);

    RSSObserver addObserver(RSSObserver observer);

    void removeObserver(RSSObserver observer);
}
