package com.travel.feature.transportation.domain.model;

public final class Transportation {

    private final String id;
    private final String title;
    private final String address;
    private final String price;

    public Transportation(String id, String title, String address, String price) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String address() {
        return address;
    }

    public String price() {
        return price;
    }
}
