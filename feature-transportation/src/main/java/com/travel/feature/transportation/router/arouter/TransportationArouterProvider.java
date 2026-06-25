package com.travel.feature.transportation.router.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.travel.feature.transportation.TransportationProviderImpl;
import com.travel.feature.transportation.api.TransportationData;
import com.travel.feature.transportation.api.TransportationProvider;
import com.travel.feature.transportation.api.TransportationRoutes;
import com.travel.feature.transportation.presentation.TransportationBlankFragment;

@Route(path = TransportationRoutes.SERVICE)
public class TransportationArouterProvider implements TransportationProvider, IProvider {

    private final TransportationProvider delegate = new TransportationProviderImpl();

    @Override
    public void init(Context context) {
    }

    @Override
    public TransportationData fetchTransportationData() {
        return delegate.fetchTransportationData();
    }

    @Override
    public Object createBlankFragment(String message) {
        return TransportationBlankFragment.newInstance(message);
    }
}
