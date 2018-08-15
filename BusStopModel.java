package com.example.lenovo.model;

public class BusStopModel {

    String stop_id="";
    String location="";
    String lattitude="";
    String longitude="";
    String stop_type="";

    public BusStopModel() {
    }

    public BusStopModel(String stop_id, String location, String lattitude, String longitude, String stop_type) {
        this.stop_id = stop_id;
        this.location = location;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.stop_type = stop_type;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getStop_type() {
        return stop_type;
    }

    public void setStop_type(String stop_type) {
        this.stop_type = stop_type;
    }
}
