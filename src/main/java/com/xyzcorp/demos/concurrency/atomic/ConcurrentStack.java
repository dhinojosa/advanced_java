package com.xyzcorp.demos.concurrency.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ConcurrentStack
 * <p/>
 * Nonblocking stack using Treiber's algorithm
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ConcurrentStack {
    // a reference to a node -- initially set to null
    AtomicReference<Node> top = new AtomicReference<>();

    public void push(Object item) {
        Node newHead = new Node(item);
        Node oldHead;
        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public Object pop() {
        Node oldHead;
        Node newHead;
        do {
            oldHead = top.get();
            if (oldHead == null)
                return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }

    private static class Node  {
        public final Object item;
        public Node next;

        public Node(Object item) {
            this.item = item;
        }
    }
}
