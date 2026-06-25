package com.travel.feature.attraction.api;

/**
 * Route contract owned by the attraction feature.
 */
public final class AttractionRoutes {

    private AttractionRoutes() {
    }

    public static final String ACTIVITY_MAIN = "/attraction/mainActivity";
    public static final String SERVICE = "/attraction/service";

    public static final class MainActivityParams {

        private MainActivityParams() {
        }

        public static final String CITY_ID = "cityId";
        public static final String ATTRACTION_ID_LIST = "attractionIdList";
    }
}
