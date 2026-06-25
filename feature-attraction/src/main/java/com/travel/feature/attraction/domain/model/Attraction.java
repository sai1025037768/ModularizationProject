package com.travel.feature.attraction.domain.model;

public final class Attraction {

    private final String id;
    private final String name;
    private final String ticketPrice;
    private final String address;

    public Attraction(String id, String name, String ticketPrice, String address) {
        this.id = id;
        this.name = name;
        this.ticketPrice = ticketPrice;
        this.address = address;
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String ticketPrice() {
        return ticketPrice;
    }

    public String address() {
        return address;
    }
}
