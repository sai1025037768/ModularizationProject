package com.travel.core.navigation;

/**
 * Application-level entry point for navigation.
 */
public final class Navigation {

    private static volatile Navigator navigator = new MissingNavigator();

    private Navigation() {
    }

    public static void setNavigator(Navigator navigator) {
        if (navigator == null) {
            throw new IllegalArgumentException("Navigator must not be null.");
        }
        Navigation.navigator = navigator;
    }

    public static <T> T navigate(RouteRequest request) {
        return navigator.navigate(request);
    }

    private static final class MissingNavigator implements Navigator {

        @Override
        public <T> T navigate(RouteRequest request) {
            throw new IllegalStateException("Navigator has not been initialized.");
        }
    }
}
