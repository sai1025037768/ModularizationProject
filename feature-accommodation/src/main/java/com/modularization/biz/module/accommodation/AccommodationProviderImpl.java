package com.modularization.biz.module.accommodation;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.modularization.biz.module.accommodation.http.ApiServiceWrap;
import com.modularization.biz.module.accommodation.model.GitHubUser;
import com.modularization.biz.service.accommodation.AccommodationApiData;
import com.modularization.biz.service.accommodation.AccommodationProvider;
import com.modularization.biz.service.accommodation.AccommodationData;
import com.modularization.biz.service.accommodation.AccommodationRouterTable;
import com.modularization.biz.service.base.ErrorMessage;
import com.modularization.biz.service.base.ResponseCallback;
import com.modularization.data.net.ApiClient;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
@Route(path = AccommodationRouterTable.PATH_SERVICE_ACCOMMODATION)
public class AccommodationProviderImpl implements AccommodationProvider {

    @Override
    public void init(Context context) {

    }

    @Override
    public AccommodationData fetchAccommodationData() {

        AccommodationData data = new AccommodationData();
        data.setId("1");
        data.setTitle("西湖湖景酒店");
        data.setAddress("杭州市西湖区南山路");
        data.setPrice("688元/晚");
        return data;
    }

    @Override
    public Subscription callAccommodationApi(ResponseCallback<AccommodationApiData> callback) {
        return ApiClient.getInstance().getService(new ApiServiceWrap()).getGitHubUser("BaronZ88").subscribeOn(Schedulers.io())
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
                        if (gitHubUser != null) {
                            AccommodationApiData data = new AccommodationApiData();
                            data.setAvatarUrl(gitHubUser.getAvatarUrl());
                            data.setBio(gitHubUser.getBio());
                            data.setBlog(gitHubUser.getBlog());
                            data.setCompany(gitHubUser.getCompany());
                            data.setEmail(gitHubUser.getEmail());
                            data.setFollowers(gitHubUser.getFollowers());
                            data.setLocation(gitHubUser.getLocation());
                            data.setName(gitHubUser.getName());
                            callback.onSuccess(data);
                        } else {
                            callback.onFailed(new ErrorMessage(405, "未获取到数据"));
                        }
                    }
                });
    }
}
