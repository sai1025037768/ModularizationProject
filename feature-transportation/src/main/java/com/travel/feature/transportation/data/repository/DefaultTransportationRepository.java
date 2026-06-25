package com.travel.feature.transportation.data.repository;

import com.travel.feature.transportation.domain.model.Transportation;
import com.travel.feature.transportation.domain.repository.TransportationRepository;

public final class DefaultTransportationRepository implements TransportationRepository {

    @Override
    public Transportation getFeaturedTransportation() {
        return new Transportation(
                "123123123123342",
                "G7356 高铁",
                "上海虹桥 -> 杭州东",
                "73元起"
        );
    }
}
