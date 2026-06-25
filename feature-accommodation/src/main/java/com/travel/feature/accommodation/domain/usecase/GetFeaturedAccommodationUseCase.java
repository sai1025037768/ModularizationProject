package com.travel.feature.accommodation.domain.usecase;

import com.travel.feature.accommodation.domain.model.Accommodation;
import com.travel.feature.accommodation.domain.repository.AccommodationRepository;

public final class GetFeaturedAccommodationUseCase {

    private final AccommodationRepository repository;

    public GetFeaturedAccommodationUseCase(AccommodationRepository repository) {
        this.repository = repository;
    }

    public Accommodation execute() {
        return repository.getFeaturedAccommodation();
    }
}
