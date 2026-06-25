package com.travel.core.navigation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Framework-neutral route request.
 */
public final class RouteRequest {

    private final String path;
    private final Map<String, Parameter> parameters = new LinkedHashMap<>();
    private Object navigationContext;

    private RouteRequest(String path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Route path must not be empty.");
        }
        this.path = path;
    }

    public static RouteRequest of(String path) {
        return new RouteRequest(path);
    }

    public String path() {
        return path;
    }

    public RouteRequest withNavigationContext(Object navigationContext) {
        this.navigationContext = navigationContext;
        return this;
    }

    public Object navigationContext() {
        return navigationContext;
    }

    public RouteRequest withString(String key, String value) {
        return putParameter(key, ParameterType.STRING, value);
    }

    public RouteRequest withInt(String key, int value) {
        return putParameter(key, ParameterType.INT, value);
    }

    public RouteRequest withIntegerArrayList(String key, ArrayList<Integer> value) {
        return putParameter(key, ParameterType.INTEGER_ARRAY_LIST, value);
    }

    public RouteRequest withParcelable(String key, Object value) {
        return putParameter(key, ParameterType.PARCELABLE, value);
    }

    public RouteRequest withParcelableArrayList(String key, ArrayList<?> value) {
        return putParameter(key, ParameterType.PARCELABLE_ARRAY_LIST, value);
    }

    public Map<String, Parameter> parameters() {
        return Collections.unmodifiableMap(parameters);
    }

    private RouteRequest putParameter(String key, ParameterType type, Object value) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Route parameter key must not be empty.");
        }
        parameters.put(key, new Parameter(type, value));
        return this;
    }

    public enum ParameterType {
        STRING,
        INT,
        INTEGER_ARRAY_LIST,
        PARCELABLE,
        PARCELABLE_ARRAY_LIST
    }

    public static final class Parameter {

        private final ParameterType type;
        private final Object value;

        private Parameter(ParameterType type, Object value) {
            this.type = type;
            this.value = value;
        }

        public ParameterType type() {
            return type;
        }

        public Object value() {
            return value;
        }
    }
}
