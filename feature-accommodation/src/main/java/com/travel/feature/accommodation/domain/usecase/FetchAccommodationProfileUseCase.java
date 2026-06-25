package com.travel.feature.accommodation.domain.usecase;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;
import com.travel.feature.accommodation.domain.repository.AccommodationRepository;

public final class FetchAccommodationProfileUseCase {

    private final AccommodationRepository repository;

    public FetchAccommodationProfileUseCase(AccommodationRepository repository) {
        this.repository = repository;
    }

    public Cancelable execute(String userName, ResponseCallback<AccommodationProfile> callback) {
        return repository.fetchProfile(userName, callback);
    }
}
