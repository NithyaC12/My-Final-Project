package com.example.lenovo.model;

public class RatingModel {

    String rating_id="";
    String bus_id="";
    String passenger_id="";
    String bus_rating="";
    String date="";

    public RatingModel() {
    }

    public RatingModel(String rating_id, String bus_id, String passenger_id, String bus_rating, String date) {
        this.rating_id = rating_id;
        this.bus_id = bus_id;
        this.passenger_id = passenger_id;
        this.bus_rating = bus_rating;
        this.date = date;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getPassenger_id() {
        return passenger_id;
    }

    public void setPassenger_id(String passenger_id) {
        this.passenger_id = passenger_id;
    }

    public String getBus_rating() {
        return bus_rating;
    }

    public void setBus_rating(String bus_rating) {
        this.bus_rating = bus_rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
