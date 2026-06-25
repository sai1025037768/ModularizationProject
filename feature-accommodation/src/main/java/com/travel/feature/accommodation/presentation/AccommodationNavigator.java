package com.travel.feature.accommodation.presentation;

import com.travel.core.navigation.Navigation;
import com.travel.core.navigation.RouteRequest;
import com.travel.feature.attraction.api.AttractionRoutes;

import java.util.ArrayList;

public final class AccommodationNavigator {

    public void openAttractions(String cityId, ArrayList<Integer> attractionIdList) {
        Navigation.navigate(RouteRequest.of(AttractionRoutes.ACTIVITY_MAIN)
                .withString(AttractionRoutes.MainActivityParams.CITY_ID, cityId)
                .withIntegerArrayList(AttractionRoutes.MainActivityParams.ATTRACTION_ID_LIST, attractionIdList));
    }
}
