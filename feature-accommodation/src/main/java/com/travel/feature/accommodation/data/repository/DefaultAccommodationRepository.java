package com.travel.feature.accommodation.data.repository;

import com.travel.biz.service.base.Cancelable;
import com.travel.biz.service.base.ErrorMessage;
import com.travel.biz.service.base.ResponseCallback;
import com.travel.feature.accommodation.data.mapper.GitHubUserMapper;
import com.travel.feature.accommodation.data.remote.AccommodationRemoteDataSource;
import com.travel.feature.accommodation.data.remote.GitHubUser;
import com.travel.feature.accommodation.domain.model.Accommodation;
import com.travel.feature.accommodation.domain.model.AccommodationProfile;
import com.travel.feature.accommodation.domain.repository.AccommodationRepository;

public final class DefaultAccommodationRepository implements AccommodationRepository {

    private final AccommodationRemoteDataSource remoteDataSource;
    private final GitHubUserMapper gitHubUserMapper;

    public DefaultAccommodationRepository(AccommodationRemoteDataSource remoteDataSource,
                                          GitHubUserMapper gitHubUserMapper) {
        this.remoteDataSource = remoteDataSource;
        this.gitHubUserMapper = gitHubUserMapper;
    }

    @Override
    public Accommodation getFeaturedAccommodation() {
        return new Accommodation(
                "1",
                "西湖湖景酒店",
                "杭州市西湖区南山路",
                "688元/晚"
        );
    }

    @Override
    public Cancelable fetchProfile(String userName, ResponseCallback<AccommodationProfile> callback) {
        return remoteDataSource.fetchGitHubUser(userName, new ResponseCallback<GitHubUser>() {
            @Override
            public void onSuccess(GitHubUser user) {
                AccommodationProfile profile = gitHubUserMapper.toProfile(user);
                if (profile == null) {
                    callback.onFailed(new ErrorMessage(405, "未获取到数据"));
                    return;
                }
                callback.onSuccess(profile);
            }

            @Override
            public void onFailed(ErrorMessage errorMsg) {
                callback.onFailed(errorMsg);
            }
        });
    }
}
