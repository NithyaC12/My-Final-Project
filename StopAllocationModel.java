package com.example.lenovo.model;

public class StopAllocationModel {

    String stop_allocation_id="";
    String route_id="";
    String stop_id="";
    String stop_number="";
    String ordinary="";
    String limited_stop="";
    String fast_passenger="";
    String super_fast="";
    String super_express="";
    String super_delux="";

    public StopAllocationModel() {
    }

    public StopAllocationModel(String stop_allocation_id, String route_id, String stop_id, String stop_number, String ordinary, String limited_stop, String fast_passenger, String super_fast, String super_express, String super_delux) {
        this.stop_allocation_id = stop_allocation_id;
        this.route_id = route_id;
        this.stop_id = stop_id;
        this.stop_number = stop_number;
        this.ordinary = ordinary;
        this.limited_stop = limited_stop;
        this.fast_passenger = fast_passenger;
        this.super_fast = super_fast;
        this.super_express = super_express;
        this.super_delux = super_delux;
    }

    public String getStop_allocation_id() {
        return stop_allocation_id;
    }

    public void setStop_allocation_id(String stop_allocation_id) {
        this.stop_allocation_id = stop_allocation_id;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_number() {
        return stop_number;
    }

    public void setStop_number(String stop_number) {
        this.stop_number = stop_number;
    }

    public String getOrdinary() {
        return ordinary;
    }

    public void setOrdinary(String ordinary) {
        this.ordinary = ordinary;
    }

    public String getLimited_stop() {
        return limited_stop;
    }

    public void setLimited_stop(String limited_stop) {
        this.limited_stop = limited_stop;
    }

    public String getFast_passenger() {
        return fast_passenger;
    }

    public void setFast_passenger(String fast_passenger) {
        this.fast_passenger = fast_passenger;
    }

    public String getSuper_fast() {
        return super_fast;
    }

    public void setSuper_fast(String super_fast) {
        this.super_fast = super_fast;
    }

    public String getSuper_express() {
        return super_express;
    }

    public void setSuper_express(String super_express) {
        this.super_express = super_express;
    }

    public String getSuper_delux() {
        return super_delux;
    }

    public void setSuper_delux(String super_delux) {
        this.super_delux = super_delux;
    }
}
