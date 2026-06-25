package com.travel.feature.accommodation.domain.model;

public final class AccommodationProfile {

    private final String avatarUrl;
    private final String name;
    private final String company;
    private final String blog;
    private final String location;
    private final Object email;
    private final String bio;
    private final int followers;

    public AccommodationProfile(String avatarUrl,
                                String name,
                                String company,
                                String blog,
                                String location,
                                Object email,
                                String bio,
                                int followers) {
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.company = company;
        this.blog = blog;
        this.location = location;
        this.email = email;
        this.bio = bio;
        this.followers = followers;
    }

    public String avatarUrl() {
        return avatarUrl;
    }

    public String name() {
        return name;
    }

    public String company() {
        return company;
    }

    public String blog() {
        return blog;
    }

    public String location() {
        return location;
    }

    public Object email() {
        return email;
    }

    public String bio() {
        return bio;
    }

    public int followers() {
        return followers;
    }
}
