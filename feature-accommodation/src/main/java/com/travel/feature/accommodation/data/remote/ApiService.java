package com.travel.feature.accommodation.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    @GET("users/{user}")
    Observable<GitHubUser> getGitHubUser(@Path("user") String userName);
}
