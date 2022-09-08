package io.github.kahar.streams.reactive;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;

import static java.util.concurrent.Flow.Subscription;

public class EndSubscriber<T> implements Flow.Subscriber<T> {
    public List<T> consumedElements = new LinkedList<>();
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        System.out.println("Got : " + item);
        consumedElements.add(item);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println("Done");
    }
}