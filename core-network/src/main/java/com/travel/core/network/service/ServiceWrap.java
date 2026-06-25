package com.travel.core.network.service;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public interface ServiceWrap<T> {

    String getIdentify();

    String getModuleName();

    String getHost();

    Class<T> getRealService();
}
