package com.travel.feature.transportation.contract;

import com.travel.feature.transportation.api.TransportationData;
import com.travel.feature.transportation.domain.model.Transportation;

public final class TransportationContractMapper {

    public TransportationData toApiData(Transportation transportation) {
        TransportationData data = new TransportationData();
        data.setId(transportation.id());
        data.setTitle(transportation.title());
        data.setAddress(transportation.address());
        data.setPrice(transportation.price());
        return data;
    }
}
