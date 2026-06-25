package com.travel.feature.accommodation.domain.repository;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.feature.accommodation.domain.model.Accommodation;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;

public interface AccommodationRepository {

    Accommodation getFeaturedAccommodation();

    Cancelable fetchProfile(String userName, ResponseCallback<AccommodationProfile> callback);
}
