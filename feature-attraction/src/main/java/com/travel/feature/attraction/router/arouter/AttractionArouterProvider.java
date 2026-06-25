package com.travel.feature.attraction.router.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.template.IProvider;
import com.travel.feature.attraction.AttractionProviderImpl;
import com.travel.feature.attraction.api.AttractionData;
import com.travel.feature.attraction.api.AttractionProvider;
import com.travel.feature.attraction.api.AttractionRoutes;

@Route(path = AttractionRoutes.SERVICE)
public class AttractionArouterProvider implements AttractionProvider, IProvider {

    private final AttractionProvider delegate = new AttractionProviderImpl();

    @Override
    public void init(Context context) {
    }

    @Override
    public AttractionData fetchAttractionData() {
        return delegate.fetchAttractionData();
    }
}
