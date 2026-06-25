package com.travel.feature.accommodation.data.remote;

import com.travel.core.network.service.ServiceWrap;

public class ApiServiceWrap implements ServiceWrap<ApiService> {

    private static final String SERVICE_ID = "id_accommodation";
    private static final String MODULE_NAME = "AccommodationModule";
    private static final String HOST = "https://api.github.com/";

    @Override
    public String getIdentify() {
        return SERVICE_ID;
    }

    @Override
    public String getModuleName() {
        return MODULE_NAME;
    }

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    public Class<ApiService> getRealService() {
        return ApiService.class;
    }
}
