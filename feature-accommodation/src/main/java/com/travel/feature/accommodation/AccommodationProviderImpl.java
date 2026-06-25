package com.travel.feature.accommodation;

import com.travel.feature.accommodation.api.AccommodationData;
import com.travel.feature.accommodation.api.AccommodationApiData;
import com.travel.feature.accommodation.api.AccommodationProvider;
import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ErrorMessage;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.feature.accommodation.contract.AccommodationContractMapper;
import com.travel.feature.accommodation.di.AccommodationComponent;
import com.travel.feature.accommodation.domain.model.Accommodation;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class AccommodationProviderImpl implements AccommodationProvider {

    private static final String DEFAULT_PROFILE_USER = "BaronZ88";

    private final AccommodationComponent component = AccommodationComponent.getInstance();
    private final AccommodationContractMapper contractMapper = new AccommodationContractMapper();

    @Override
    public AccommodationData fetchAccommodationData() {
        Accommodation accommodation = component.getFeaturedAccommodationUseCase().execute();
        return contractMapper.toApiData(accommodation);
    }

    @Override
    public Cancelable callAccommodationApi(ResponseCallback<AccommodationApiData> callback) {
        return component.fetchAccommodationProfileUseCase().execute(DEFAULT_PROFILE_USER, new ResponseCallback<AccommodationProfile>() {
            @Override
            public void onSuccess(AccommodationProfile profile) {
                callback.onSuccess(contractMapper.toApiData(profile));
            }

            @Override
            public void onFailed(ErrorMessage errorMsg) {
                callback.onFailed(errorMsg);
            }
        });
    }
}
