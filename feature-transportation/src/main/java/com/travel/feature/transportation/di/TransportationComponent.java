package com.travel.feature.transportation.di;

import com.travel.feature.transportation.data.repository.DefaultTransportationRepository;
import com.travel.feature.transportation.domain.repository.TransportationRepository;
import com.travel.feature.transportation.domain.usecase.GetFeaturedTransportationUseCase;

public final class TransportationComponent {

    private static final TransportationComponent INSTANCE = new TransportationComponent();

    private final GetFeaturedTransportationUseCase getFeaturedTransportationUseCase;

    private TransportationComponent() {
        TransportationRepository repository = new DefaultTransportationRepository();
        getFeaturedTransportationUseCase = new GetFeaturedTransportationUseCase(repository);
    }

    public static TransportationComponent getInstance() {
        return INSTANCE;
    }

    public GetFeaturedTransportationUseCase getFeaturedTransportationUseCase() {
        return getFeaturedTransportationUseCase;
    }
}
