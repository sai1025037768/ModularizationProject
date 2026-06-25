package com.travel.biz.service.base;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public interface ResponseCallback<T> {

    void onSuccess(T t);

    void onFailed(ErrorMessage errorMsg);
}
