package com.travel.feature.accommodation.data.remote;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ErrorMessage;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.core.network.ApiClient;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public final class AccommodationRemoteDataSource {

    public Cancelable fetchGitHubUser(String userName, ResponseCallback<GitHubUser> callback) {
        Subscription subscription = ApiClient.getInstance()
                .getService(new ApiServiceWrap())
                .getGitHubUser(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubUser>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onFailed(new ErrorMessage(403, e.getMessage()));
                    }

                    @Override
                    public void onNext(GitHubUser gitHubUser) {
                        callback.onSuccess(gitHubUser);
                    }
                });
        return new RxSubscriptionCancelable(subscription);
    }
}
