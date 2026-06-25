package com.travel.feature.accommodation.contract;

import com.travel.feature.accommodation.api.AccommodationApiData;
import com.travel.feature.accommodation.api.AccommodationData;
import com.travel.feature.accommodation.domain.model.Accommodation;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;

public final class AccommodationContractMapper {

    public AccommodationData toApiData(Accommodation accommodation) {
        AccommodationData data = new AccommodationData();
        data.setId(accommodation.id());
        data.setTitle(accommodation.title());
        data.setAddress(accommodation.address());
        data.setPrice(accommodation.price());
        return data;
    }

    public AccommodationApiData toApiData(AccommodationProfile profile) {
        AccommodationApiData data = new AccommodationApiData();
        data.setAvatarUrl(profile.avatarUrl());
        data.setName(profile.name());
        data.setCompany(profile.company());
        data.setBlog(profile.blog());
        data.setLocation(profile.location());
        data.setEmail(profile.email());
        data.setBio(profile.bio());
        data.setFollowers(profile.followers());
        return data;
    }
}
