package com.travel.feature.attraction.api;

/**
 * @author byron (byron[dot]zhanglei[at]gmail[dot]com)
 */
public class AttractionData {

    private String id;
    private String name;
    private String ticketPrice;
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return " AttractionData:\n" +
                "   id=" + id + "\n" +
                "   name=" + name + "\n" +
                "   ticketPrice=" + ticketPrice + "\n" +
                "   address=" + address;
    }
}
