package com.example.lenovo.model;

public class DriverModel {

    String driver_id="";
    String login_id="";
    String bus_id="";
    String driver_name="";
    String license_number="";

    public DriverModel() {
    }

    public DriverModel(String driver_id, String login_id, String bus_id, String driver_name, String license_number) {
        this.driver_id = driver_id;
        this.login_id = login_id;
        this.bus_id = bus_id;
        this.driver_name = driver_name;
        this.license_number = license_number;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public String getBus_id() {
        return bus_id;
    }

    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }
}
