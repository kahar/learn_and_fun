package io.github.kahar.streams.reactive;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.Flow.Subscription;

public class CustomEndSubscriber<T> implements Flow.Subscriber<T> {
    public List<T> consumedElements = new LinkedList<>();
    private AtomicInteger howMuchMessagesConsume;
    private Subscription subscription;

    public CustomEndSubscriber(Integer howMuchMessagesConsume) {
        this.howMuchMessagesConsume
                = new AtomicInteger(howMuchMessagesConsume);
    }

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        howMuchMessagesConsume.decrementAndGet();
        System.out.println("Got : " + item);
        consumedElements.add(item);
        if (howMuchMessagesConsume.get() > 0) {
            subscription.request(1);
        }
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
