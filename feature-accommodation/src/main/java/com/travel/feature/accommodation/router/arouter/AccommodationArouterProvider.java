package com.travel.feature.accommodation.router.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.feature.accommodation.AccommodationProviderImpl;
import com.travel.feature.accommodation.api.AccommodationApiData;
import com.travel.feature.accommodation.api.AccommodationData;
import com.travel.feature.accommodation.api.AccommodationProvider;
import com.travel.feature.accommodation.api.AccommodationRoutes;

@Route(path = AccommodationRoutes.SERVICE)
public class AccommodationArouterProvider implements AccommodationProvider, IProvider {

    private final AccommodationProvider delegate = new AccommodationProviderImpl();

    @Override
    public void init(Context context) {
    }

    @Override
    public AccommodationData fetchAccommodationData() {
        return delegate.fetchAccommodationData();
    }

    @Override
    public Cancelable callAccommodationApi(ResponseCallback<AccommodationApiData> callback) {
        return delegate.callAccommodationApi(callback);
    }
}
