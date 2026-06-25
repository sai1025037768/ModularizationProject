package com.travel.core.navigation;

/**
 * Framework-neutral navigation capability.
 */
public interface Navigator {

    <T> T navigate(RouteRequest request);
}
