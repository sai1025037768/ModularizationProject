package com.travel.feature.attraction.data.repository;

import com.travel.feature.attraction.domain.model.Attraction;
import com.travel.feature.attraction.domain.repository.AttractionRepository;

public final class DefaultAttractionRepository implements AttractionRepository {

    @Override
    public Attraction getFeaturedAttraction() {
        return new Attraction(
                "123123123",
                "西湖风景名胜区",
                "免费",
                "杭州市西湖区龙井路"
        );
    }
}
