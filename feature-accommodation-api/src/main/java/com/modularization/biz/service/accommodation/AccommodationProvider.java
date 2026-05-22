package com.modularization.biz.service.accommodation;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.modularization.biz.service.base.ResponseCallback;

import rx.Subscription;

/**
 * @author baronzhang (baron[dot]zhanglei[at]gmail[dot]com)
 */
public interface AccommodationProvider extends IProvider {

    AccommodationData fetchAccommodationData();

    Subscription callAccommodationApi(ResponseCallback<AccommodationApiData> callback);
}