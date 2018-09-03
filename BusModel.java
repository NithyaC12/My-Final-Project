package com.example.lenovo.model;

public class BusModel {

    String bus_id="";
    String user_id="";
    String bus_name="";
    String bus_number="";
    String imei="";
    String bus_type="";
    String bus_image="";
    String status="";

    public BusModel() {
    }

    public BusModel(String bus_id, String user_id, String bus_name, String bus_number, String imei, String bus_type, String bus_image, String status) {
        this.bus_id = bus_id;
        this.user_id = user_id;
        this.bus_name = bus_name;
        this.bus_number = bus_number;
        this.imei = imei;
        this.bus_type = bus_type;
        this.bus_image = bus_image;
        this.status = status;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getBus_type() {
        return bus_type;
    }

    public void setBus_type(String bus_type) {
        this.bus_type = bus_type;
    }

    public String getBus_image() {
        return bus_image;
    }

    public void setBus_image(String bus_image) {
        this.bus_image = bus_image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
