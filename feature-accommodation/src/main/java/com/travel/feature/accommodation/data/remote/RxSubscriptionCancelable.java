package com.travel.feature.accommodation.data.remote;

import com.travel.biz.service.base.Cancelable;

import rx.Subscription;

final class RxSubscriptionCancelable implements Cancelable {

    private final Subscription subscription;

    RxSubscriptionCancelable(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public void cancel() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public boolean isCanceled() {
        return subscription.isUnsubscribed();
    }
}
