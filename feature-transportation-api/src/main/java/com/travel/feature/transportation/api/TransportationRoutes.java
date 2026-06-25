package com.travel.feature.transportation.api;

/**
 * Route contract owned by the transportation feature.
 */
public final class TransportationRoutes {

    private TransportationRoutes() {
    }

    public static final String ACTIVITY_MAIN = "/transportation/mainActivity";
    public static final String SERVICE = "/transportation/service";

    public static final class MainActivityParams {

        private MainActivityParams() {
        }

        public static final String CITY_ID = "cityId";
        public static final String TRAVEL_PLAN_LIST = "travelPlanList";
    }

}
