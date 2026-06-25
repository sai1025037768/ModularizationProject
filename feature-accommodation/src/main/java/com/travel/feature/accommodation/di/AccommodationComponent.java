package com.travel.feature.accommodation.di;

import com.travel.feature.accommodation.data.mapper.GitHubUserMapper;
import com.travel.feature.accommodation.data.remote.AccommodationRemoteDataSource;
import com.travel.feature.accommodation.data.repository.DefaultAccommodationRepository;
import com.travel.feature.accommodation.domain.repository.AccommodationRepository;
import com.travel.feature.accommodation.domain.usecase.FetchAccommodationProfileUseCase;
import com.travel.feature.accommodation.domain.usecase.GetFeaturedAccommodationUseCase;

public final class AccommodationComponent {

    private static final AccommodationComponent INSTANCE = new AccommodationComponent();

    private final GetFeaturedAccommodationUseCase getFeaturedAccommodationUseCase;
    private final FetchAccommodationProfileUseCase fetchAccommodationProfileUseCase;

    private AccommodationComponent() {
        AccommodationRepository repository = new DefaultAccommodationRepository(
                new AccommodationRemoteDataSource(),
                new GitHubUserMapper()
        );
        getFeaturedAccommodationUseCase = new GetFeaturedAccommodationUseCase(repository);
        fetchAccommodationProfileUseCase = new FetchAccommodationProfileUseCase(repository);
    }

    public static AccommodationComponent getInstance() {
        return INSTANCE;
    }

    public GetFeaturedAccommodationUseCase getFeaturedAccommodationUseCase() {
        return getFeaturedAccommodationUseCase;
    }

    public FetchAccommodationProfileUseCase fetchAccommodationProfileUseCase() {
        return fetchAccommodationProfileUseCase;
    }
}
