package com.travel.feature.attraction.domain.usecase;

import com.travel.feature.attraction.domain.model.Attraction;
import com.travel.feature.attraction.domain.repository.AttractionRepository;

public final class GetFeaturedAttractionUseCase {

    private final AttractionRepository repository;

    public GetFeaturedAttractionUseCase(AttractionRepository repository) {
        this.repository = repository;
    }

    public Attraction execute() {
        return repository.getFeaturedAttraction();
    }
}
