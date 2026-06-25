package com.travel.feature.attraction.contract;

import com.travel.feature.attraction.api.AttractionData;
import com.travel.feature.attraction.domain.model.Attraction;

public final class AttractionContractMapper {

    public AttractionData toApiData(Attraction attraction) {
        AttractionData data = new AttractionData();
        data.setId(attraction.id());
        data.setName(attraction.name());
        data.setTicketPrice(attraction.ticketPrice());
        data.setAddress(attraction.address());
        return data;
    }
}
