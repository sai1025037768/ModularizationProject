package com.travel.feature.attraction.di;

import com.travel.feature.attraction.data.repository.DefaultAttractionRepository;
import com.travel.feature.attraction.domain.repository.AttractionRepository;
import com.travel.feature.attraction.domain.usecase.GetFeaturedAttractionUseCase;

public final class AttractionComponent {

    private static final AttractionComponent INSTANCE = new AttractionComponent();

    private final GetFeaturedAttractionUseCase getFeaturedAttractionUseCase;

    private AttractionComponent() {
        AttractionRepository repository = new DefaultAttractionRepository();
        getFeaturedAttractionUseCase = new GetFeaturedAttractionUseCase(repository);
    }

    public static AttractionComponent getInstance() {
        return INSTANCE;
    }

    public GetFeaturedAttractionUseCase getFeaturedAttractionUseCase() {
        return getFeaturedAttractionUseCase;
    }
}
