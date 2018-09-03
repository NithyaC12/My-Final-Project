package com.example.lenovo.model;

public class BusLocationModel {

    String location_id="";
    String place_name="";
    String lattitude="";
    String longitude="";
    String bus_id="";
    String last_updation="";
    String status="";

    public BusLocationModel() {
    }

    public BusLocationModel(String location_id, String place_name, String lattitude, String longitude, String bus_id, String last_updation, String status) {
        this.location_id = location_id;
        this.place_name = place_name;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.bus_id = bus_id;
        this.last_updation = last_updation;
        this.status = status;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getLast_updation() {
        return last_updation;
    }

    public void setLast_updation(String last_updation) {
        this.last_updation = last_updation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
