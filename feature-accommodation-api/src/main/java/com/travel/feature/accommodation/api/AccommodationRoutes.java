package com.travel.feature.accommodation.api;

/**
 * Route contract owned by the accommodation feature.
 */
public final class AccommodationRoutes {

    private AccommodationRoutes() {
    }

    public static final String ACTIVITY_MAIN = "/accommodation/mainActivity";
    public static final String SERVICE = "/accommodation/service";

    public static final class MainActivityParams {

        private MainActivityParams() {
        }

        public static final String CITY_ID = "cityId";
        public static final String TRAVEL_DETAIL = "travelDetail";
    }
}
