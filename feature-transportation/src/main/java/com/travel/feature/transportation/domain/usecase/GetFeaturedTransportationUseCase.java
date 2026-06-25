package com.travel.feature.transportation.domain.usecase;

import com.travel.feature.transportation.domain.model.Transportation;
import com.travel.feature.transportation.domain.repository.TransportationRepository;

public final class GetFeaturedTransportationUseCase {

    private final TransportationRepository repository;

    public GetFeaturedTransportationUseCase(TransportationRepository repository) {
        this.repository = repository;
    }

    public Transportation execute() {
        return repository.getFeaturedTransportation();
    }
}
