package com.travel.feature.home.http;

import com.travel.feature.home.Constants;
import com.travel.core.network.service.ServiceWrap;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class ApiServiceWrap implements ServiceWrap<ApiService> {

    @Override
    public String getIdentify() {
        return Constants.Service.ID;
    }

    @Override
    public String getModuleName() {
        return Constants.Service.MODULE_NAME;
    }

    @Override
    public String getHost() {
        return Constants.Service.HOST;
    }

    @Override
    public Class<ApiService> getRealService() {
        return ApiService.class;
    }
}
