package com.travel.feature.accommodation.api;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ResponseCallback;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public interface AccommodationProvider {

    AccommodationData fetchAccommodationData();

    Cancelable callAccommodationApi(ResponseCallback<AccommodationApiData> callback);
}
