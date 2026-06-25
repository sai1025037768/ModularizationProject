package com.travel.feature.accommodation.data.mapper;

import com.travel.feature.accommodation.data.remote.GitHubUser;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;

public final class GitHubUserMapper {

    public AccommodationProfile toProfile(GitHubUser user) {
        if (user == null) {
            return null;
        }
        return new AccommodationProfile(
                user.getAvatarUrl(),
                user.getName(),
                user.getCompany(),
                user.getBlog(),
                user.getLocation(),
                user.getEmail(),
                user.getBio(),
                user.getFollowers()
        );
    }
}
